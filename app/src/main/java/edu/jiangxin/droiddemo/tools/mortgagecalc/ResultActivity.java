package edu.jiangxin.droiddemo.tools.mortgagecalc;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;

public class ResultActivity extends Activity {
    private static final String TAG = "ResultActivity";

    private static final int DONE = 1;

    private double mMortgage;

    /** 年利率 */
    private double mYearRate;

    /** 还款期数（每月还款一次） */
    private int mTimes;

    private int mAheadTime;

    private int firstYear;
    private int firstMonth;
    private int calculationMethod;
    private String title;
    private double sum = 0;
    private double interest = 0;
    //用于显示等额本息和等额本金的ViewPager
    private ViewPager viewPager;
    private List<View> viewList;
    private View view1;
    private View view2;
    private MyListView listViewOne;                     //等额本息的ListView
    private MyListView listViewTwo;                     //等额本金的ListView
    private TextView typeOneText;                       //等额本息的标题
    private TextView typeTwoText;                       //等额本金的标题
    private TextView oneLoanSumTextView;                //显示等额本息的结果
    private TextView oneMonthTextView;
    private TextView onePaySumTextView;
    private TextView oneInterestTextView;
    private TextView oneMonthPayTextView;
    private TextView twoLoanSumTextView;                //显示等额本金的结果
    private TextView twoMonthTextView;
    private TextView twoPaySumTextView;
    private TextView twoInterestTextView;
    private TextView twoFirstMonthPayTextView;
    private TextView twoDeltaMonthPayTextView;
    private String oneSumString;                        //等额本息的结果数据
    private String oneInterestString;
    private String oneMonthPayString;
    private String[] oneTimeStrings;
    private String[] oneCapitalStrings;
    private String[] oneInterestStrings;
    private String[] oneMonthPayStrings;
    private String twoSumString;                        //等额本金的结果数据
    private String twoInterestString;
    private String twoFistMonthSum;
    private String twoDeltaMonthSum;
    private String[] twoTimeStrings;
    private String[] twoCapitalStrings;
    private String[] twoInterestStrings;
    private String[] twoMonthPayStrings;
    private int currentItem = 0;
    private ImageView cursorImageView;
    private int offSet;
    private final Matrix matrix = new Matrix();
    private Animation animation;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DONE:
                    showResult();                       //9.显示结果

                    //等额本息的结果
                    ResultListViewAdapter adapterList1 = new ResultListViewAdapter(ResultActivity.this, oneTimeStrings, oneCapitalStrings, oneInterestStrings, oneMonthPayStrings);
                    listViewOne.setAdapter(adapterList1);

                    //等额本金的结果
                    ResultListViewAdapter adapterList2 = new ResultListViewAdapter(ResultActivity.this, twoTimeStrings, twoCapitalStrings, twoInterestStrings, twoMonthPayStrings);
                    listViewTwo.setAdapter(adapterList2);

                    viewPager.setCurrentItem(currentItem);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();                     //0.初始化
        getData();                  //1.获得数据
        initViews();                //2.初始化控件
        initViewPager();            //3.设置ViewPager
        setListeners();             //4.设置监听器

        //启动新线程来计算
        new Thread(new Runnable() {
            @Override
            public void run() {
                calculateEqualInterest(mTimes, mAheadTime);              //5.等额本息的计算方法
                sortOneStrings();                               //7.等额本金数据整理

                calculateEqualCapital(mTimes, mAheadTime);              //6.等额本金的计算方法
                sortTwoStrings();                               //8.等额本金数据整理

                handler.sendEmptyMessage(DONE);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        if (viewPager != null) {
            viewPager.clearOnPageChangeListeners();
        }
        super.onDestroy();
    }

    //0.初始化
    public void init() {
    }

    //1.获得数据
    public void getData() {
        //从前一个Activity传来的数据
        Bundle bundle = this.getIntent().getExtras();
        String m = bundle.getString("mortgage");
        String r = bundle.getString("rate");
        String t = bundle.getString("time");
        String a = bundle.getString("aheadTime");
        firstYear = bundle.getInt("firstYear");
        firstMonth = bundle.getInt("firstMonth");
        currentItem = bundle.getInt("paybackMethod");
        calculationMethod = bundle.getInt("calculationMethod");

        switch (calculationMethod) {
            case 0:
                title = "商业贷款";
                break;
            case 1:
                title = "公积金贷款";
                break;
            case 2:
                title = "组合贷款";
                break;
        }
        this.setTitle(title);

        //万元转换为元
        mMortgage = Double.valueOf(m);
        mMortgage = mMortgage * 10000;

        mYearRate = Double.valueOf(r);
        mYearRate = mYearRate / 100;

        //贷款时间转换为月
        mTimes = Integer.valueOf(t);
        mTimes = mTimes * 12;

        //第几年还款转换为月
        mAheadTime = Integer.valueOf(a);
        mAheadTime = mAheadTime * 12;
    }

    //2.初始化控件
    public void initViews() {
        viewPager = findViewById(R.id.viewpager);
        typeOneText = findViewById(R.id.typeOneTextView);
        typeTwoText = findViewById(R.id.typeTwoTextView);
        cursorImageView = findViewById(R.id.ResultCursorImageView);
    }

    //3.设置ViewPager
    public void initViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater().from(this);

        view1 = layoutInflater.inflate(R.layout.viewpager_capital_interest, null);
        view2 = layoutInflater.inflate(R.layout.viewpager_capital, null);

        viewList.add(view1);
        viewList.add(view2);

        oneLoanSumTextView = view1.findViewById(R.id.ViewPager_CapitalInterest_LoanSum_Number_TextView);
        oneMonthTextView = view1.findViewById(R.id.ViewPager_CapitalInterest_Month_Number_TextView);
        onePaySumTextView = view1.findViewById(R.id.ViewPager_CapitalInterest_PaySum_Number_TextView);
        oneInterestTextView = view1.findViewById(R.id.ViewPager_CapitalInterest_Interest_Number_TextView);
        oneMonthPayTextView = view1.findViewById(R.id.ViewPager_CapitalInterest_MonthPay_Number_TextView);
        listViewOne = view1.findViewById(R.id.listOne);

        twoLoanSumTextView = view2.findViewById(R.id.ViewPager_Capital_LoanSum_Number_TextView);
        twoMonthTextView = view2.findViewById(R.id.ViewPager_Capital_Month_Number_TextView);
        twoPaySumTextView = view2.findViewById(R.id.ViewPager_Capital_PaySum_Number_TextView);
        twoInterestTextView = view2.findViewById(R.id.ViewPager_Capital_Interest_Number_TextView);
        twoFirstMonthPayTextView = view2.findViewById(R.id.ViewPager_Capital_FirstMonthPay_Number_TextView);
        twoDeltaMonthPayTextView = view2.findViewById(R.id.ViewPager_Capital_DeltaMonthPay_Number_TextView);
        listViewTwo = view2.findViewById(R.id.listTwo);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new PageChangeListener());

        //设置光标
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        offSet = displayMetrics.widthPixels / 2;                            //每个标题的宽度（720/2=360）
        matrix.setTranslate(0, 0);
        cursorImageView.setImageMatrix(matrix);                             // 需要imageView的scaleType为matrix*/
    }

    //4.设置监听器
    public void setListeners() {
        typeOneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        typeTwoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

    }

    //5.等额本息的计算方法
    private void calculateEqualInterest(int time, int aheadTime) {
        //如果没有提前还款
        if (aheadTime == 0) {
            aheadTime = time;
        }

        double monthRate = mYearRate / 12;

        oneTimeStrings = new String[aheadTime + 1];
        oneCapitalStrings = new String[aheadTime + 1];
        oneInterestStrings = new String[aheadTime + 1];
        oneMonthPayStrings = new String[aheadTime + 1];

        String[] monthCapital = new String[time + 1];
        String[] monthInterest = new String[time + 1];
        String[] monthSum = new String[time + 1];

        double paidCapital = 0;     //已还本金
        double paidInterest = 0;    //已还利息
        double paid = 0;            //总共已还
        DecimalFormat df = new DecimalFormat("#,###.0");       //保留两位小数
        for (int i = 1; i <= aheadTime; i++) {

            //每月应还本金：每月应还本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
            monthCapital[i] = df.format(mMortgage * monthRate * Math.pow((1 + monthRate), i - 1) / (Math.pow(1 + monthRate, time) - 1));

            //每月应还利息：贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
            monthInterest[i] = df.format(mMortgage * monthRate * (Math.pow(1 + monthRate, time) - Math.pow(1 + monthRate, i - 1)) / (Math.pow(1 + monthRate, time) - 1));

            //月供：每月月供额=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
            monthSum[i] = df.format(mMortgage * monthRate * Math.pow((1 + monthRate), time) / (Math.pow(1 + monthRate, time) - 1));

            //得到输出字符串
            //strings[i] = i + "期" + "     " + monthCapital[i] + "     " + monthInterest[i] + "     " + monthSum[i];
            oneTimeStrings[i] = i + "期";
            oneCapitalStrings[i] = monthCapital[i];
            oneInterestStrings[i] = monthInterest[i];
            oneMonthPayStrings[i] = monthSum[i];

            //已还本金
            paidCapital = paidCapital + mMortgage * monthRate * Math.pow((1 + monthRate), i - 1) / (Math.pow(1 + monthRate, time) - 1);

            //已还利息
            paidInterest = paidInterest + mMortgage * monthRate * (Math.pow(1 + monthRate, time) - Math.pow(1 + monthRate, i - 1)) / (Math.pow(1 + monthRate, time) - 1);

            //总共已还
            paid = paid + mMortgage * monthRate * Math.pow((1 + monthRate), time) / (Math.pow(1 + monthRate, time) - 1);

        }

        //月供
        double monthPay = mMortgage * monthRate * Math.pow((1 + monthRate), time) / (Math.pow(1 + monthRate, time) - 1);

        //还款总额
        sum = monthPay * time;

        //还款利息总额
        interest = monthPay * time - mMortgage;

        //格式化
        oneSumString = df.format(sum);
        oneInterestString = df.format(interest);
        oneMonthPayString = df.format(monthPay);

        //提前还款的相关数据
        String pi = df.format(paidInterest);
        String rest = df.format(mMortgage - paidCapital);
        String p = df.format(paid);
    }

    /**
     * 等额本金还款方式相关数值计算
     *
     * @param time 还款月数
     * @param aheadTime 提前还款月数
     */
    private void calculateEqualCapital(int time, int aheadTime) {
        if (aheadTime == 0) {
            aheadTime = time;
        }

        double monthRate = mYearRate / 12;
        Log.i(TAG, "[calculateEqualCapital]time: " + time + ", aheadTime: " + aheadTime + ", monthRate: " + monthRate);

        twoTimeStrings = new String[aheadTime + 1];
        twoCapitalStrings = new String[aheadTime + 1];
        twoInterestStrings = new String[aheadTime + 1];
        twoMonthPayStrings = new String[aheadTime + 1];

        //String[] strings = new String[aheadTime + 1];
        String[] monthCapital = new String[time + 1];
        String[] monthInterest = new String[time + 1];
        String[] monthSum = new String[time + 1];

        DecimalFormat df = new DecimalFormat("#,###.0");
        double paid = 0;
        double paidCapital = 0;
        double paidInterest = 0;
        double paidSum = 0;
        for (int i = 1; i <= aheadTime; i++) {

            //每月应还本金：贷款本金÷还款月数
            monthCapital[i] = df.format(mMortgage / time);

            //每月应还利息：剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
            monthInterest[i] = df.format((mMortgage - paid) * monthRate);

            //月供：(贷款本金÷还款月数)+(贷款本金-已归还本金累计额)×月利率
            monthSum[i] = df.format((mMortgage / time) + (mMortgage - paid) * monthRate);

            //已归还本金累计额
            paid = paid + mMortgage / time;

            //已还本金
            paidCapital = paidCapital + mMortgage / time;

            //已还利息
            paidInterest = paidInterest + (mMortgage - paid) * monthRate;

            //总共已还
            paidSum = paidSum + (mMortgage / time) + (mMortgage - paid) * monthRate;

            //strings[i] = i + "期" + "     " + monthCapital[i] + "     "+ monthInterest[i] + "     "+ monthSum[i];

            //注意，i是从1开始的
            twoTimeStrings[i] = i + "期";
            twoCapitalStrings[i] = monthCapital[i];
            twoInterestStrings[i] = monthInterest[i];
            twoMonthPayStrings[i] = monthSum[i];
        }
        sum = time * (mMortgage * monthRate - monthRate * (mMortgage / time) * (time - 1) / 2 + mMortgage / time);
        interest = sum - mMortgage;

        twoSumString = df.format(sum);
        twoInterestString = df.format(interest);
        twoFistMonthSum = monthSum[1];

        //计算每月递减
        String firstMonth = monthSum[1].replaceAll(",", "");
        String secondMonth = monthSum[2].replaceAll(",", "");
        double delta = Double.valueOf(firstMonth) - Double.valueOf(secondMonth);
        twoDeltaMonthSum = df.format(delta);

        //提前还款的相关数据
        String p = df.format(paidSum);
        String pi = df.format(paidInterest);
        String rest = df.format(mMortgage - paidCapital);
    }

    //7.等额本息数据整理
    public void sortOneStrings() {
        List<String> timeList = new ArrayList<>();
        List<String> capitalList = new ArrayList<>();
        List<String> interestList = new ArrayList<>();
        List<String> monthPayList = new ArrayList<>();

        int deltaMonth = 12 - firstMonth + 1;
        int deltaYear = mTimes / 12;
        int max = 0;

        //开始月份不是1月
        if (deltaMonth != 12) {
            String[] years = new String[deltaYear + 1];
            for (int i = 0; i < deltaYear + 1; i++) {
                years[i] = (firstYear + i) + "年";
            }
            max = mTimes + (deltaYear + 1) - (deltaMonth + 1);

            timeList.add(years[0]);
            capitalList.add("");
            interestList.add("");
            monthPayList.add("");
            for (int i = 0; i < deltaMonth; i++) {
                timeList.add((firstMonth + i) + "月," + oneTimeStrings[i + 1]);
                capitalList.add(oneCapitalStrings[i + 1]);
                interestList.add(oneInterestStrings[i + 1]);
                monthPayList.add(oneMonthPayStrings[i + 1]);
            }

            int j = 1;
            int k = deltaMonth + 1;
            for (int i = 0; i < max; i++) {
                int index = i % 13;
                if (index == 0) {
                    timeList.add(years[j]);
                    capitalList.add("");
                    interestList.add("");
                    monthPayList.add("");
                    j++;
                } else {
                    timeList.add(index + "月," + oneTimeStrings[k]);
                    capitalList.add(oneCapitalStrings[k]);
                    interestList.add(oneInterestStrings[k]);
                    monthPayList.add(oneMonthPayStrings[k]);
                    k++;
                }
            }
        }

        //开始月份是1月
        else {
            String[] years = new String[deltaYear];
            for (int i = 0; i < deltaYear; i++) {
                years[i] = (firstYear + i) + "年";
            }
            max = mTimes + deltaYear;
            int j = 0;
            int k = 1;
            for (int i = 0; i < max; i++) {
                int index = i % 13;
                if (index == 0) {
                    timeList.add(years[j]);
                    capitalList.add("");
                    interestList.add("");
                    monthPayList.add("");
                    j++;
                } else {
                    timeList.add(index + "月," + oneTimeStrings[k]);
                    capitalList.add(oneCapitalStrings[k]);
                    interestList.add(oneInterestStrings[k]);
                    monthPayList.add(oneMonthPayStrings[k]);
                    k++;
                }
            }
        }

        oneTimeStrings = (String[]) timeList.toArray(new String[timeList.size()]);
        oneCapitalStrings = (String[]) capitalList.toArray(new String[capitalList.size()]);
        oneInterestStrings = (String[]) interestList.toArray(new String[interestList.size()]);
        oneMonthPayStrings = (String[]) monthPayList.toArray(new String[monthPayList.size()]);

        //return resultString;
    }

    //8.等额本金数据整理
    public void sortTwoStrings() {
        List<String> timeList = new ArrayList<>();
        List<String> capitalList = new ArrayList<>();
        List<String> interestList = new ArrayList<>();
        List<String> monthPayList = new ArrayList<>();

        int deltaMonth = 12 - firstMonth + 1;
        int deltaYear = mTimes / 12;
        int max = 0;

        //开始月份不是1月
        if (deltaMonth != 12) {
            String[] years = new String[deltaYear + 1];
            for (int i = 0; i < deltaYear + 1; i++) {
                years[i] = (firstYear + i) + "年";
            }

            max = mTimes + (deltaYear + 1) - (deltaMonth + 1);
            timeList.add(years[0]);
            capitalList.add("");
            interestList.add("");
            monthPayList.add("");
            for (int i = 0; i < deltaMonth; i++) {
                timeList.add((firstMonth + i) + "月," + twoTimeStrings[i + 1]);
                capitalList.add(twoCapitalStrings[i + 1]);
                interestList.add(twoInterestStrings[i + 1]);
                monthPayList.add(twoMonthPayStrings[i + 1]);
            }

            int j = 1;
            int k = deltaMonth + 1;
            for (int i = 0; i < max; i++) {
                int index = i % 13;
                if (index == 0) {
                    timeList.add(years[j]);
                    capitalList.add("");
                    interestList.add("");
                    monthPayList.add("");
                    j++;
                } else {
                    timeList.add(index + "月," + twoTimeStrings[k]);
                    capitalList.add(twoCapitalStrings[k]);
                    interestList.add(twoInterestStrings[k]);
                    monthPayList.add(twoMonthPayStrings[k]);
                    k++;
                }
            }
        }

        //开始月份是1月
        else {
            String[] years = new String[deltaYear];
            for (int i = 0; i < deltaYear; i++) {
                years[i] = (firstYear + i) + "年";
            }
            max = mTimes + deltaYear;
            int j = 0;
            int k = 1;
            for (int i = 0; i < max; i++) {
                int index = i % 13;
                if (index == 0) {
                    timeList.add(years[j]);
                    capitalList.add("");
                    interestList.add("");
                    monthPayList.add("");
                    j++;
                } else {
                    timeList.add(index + "月," + twoTimeStrings[k]);
                    capitalList.add(twoCapitalStrings[k]);
                    interestList.add(twoInterestStrings[k]);
                    monthPayList.add(twoMonthPayStrings[k]);
                    k++;
                }
            }
        }

        twoTimeStrings = (String[]) timeList.toArray(new String[timeList.size()]);
        twoCapitalStrings = (String[]) capitalList.toArray(new String[capitalList.size()]);
        twoInterestStrings = (String[]) interestList.toArray(new String[interestList.size()]);
        twoMonthPayStrings = (String[]) monthPayList.toArray(new String[monthPayList.size()]);

        //return resultString;
    }

    //9.显示结果
    public void showResult() {
        DecimalFormat df = new DecimalFormat("#,###.0");

        if (mAheadTime == 0) {
            //等额本息的结果
            oneLoanSumTextView.setText(df.format(mMortgage / 10000) + "万元");
            oneMonthTextView.setText(mTimes + "月");
            onePaySumTextView.setText(oneSumString + "元");
            oneInterestTextView.setText(oneInterestString + "元");
            oneMonthPayTextView.setText(oneMonthPayString + "元");

            //等额本金的结果
            twoLoanSumTextView.setText(df.format(mMortgage / 10000) + "万元");
            twoMonthTextView.setText(mTimes + "月");
            twoPaySumTextView.setText(twoSumString + "元");
            twoInterestTextView.setText(twoInterestString + "元");
            twoFirstMonthPayTextView.setText(twoFistMonthSum + "元");
            twoDeltaMonthPayTextView.setText(twoDeltaMonthSum + "元");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //按下返回键，返回到首页
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ResultActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //3-1.ViewPager的监听器
    public class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //设置光标
            switch (position) {
                case 0:
                    animation = new TranslateAnimation(offSet, 0, 0, 0);
                    break;
                case 1:
                    animation = new TranslateAnimation(0, offSet, 0, 0);
                    break;
            }
            currentItem = position;
            animation.setDuration(150); // 光标滑动速度
            animation.setFillAfter(true);
            cursorImageView.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
