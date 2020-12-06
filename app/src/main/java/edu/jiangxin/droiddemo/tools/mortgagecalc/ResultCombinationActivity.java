package edu.jiangxin.droiddemo.tools.mortgagecalc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
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

public class ResultCombinationActivity extends Activity {
    private static final int DONE = 1;
    private double mortgage;                    //贷款总额
    private int time;                           //贷款时间
    private double commMortgage;                //商业贷款总额
    private double HAFMortgage;                 //公积金贷款总额
    private double commRate;                    //商业贷款利率
    private double HAFRate;                     //公积金贷款利率
    private double commMonthRate;               //商业贷款月利率
    private double HAFMonthRate;                //公积金贷款月利率
    private int aheadTime;
    private int firstYear;
    private int firstMonth;
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
    private TextView onePaySumCommTextView;
    private TextView oneInterestCommTextView;
    private TextView onePaySumHAFTextView;
    private TextView oneInterestHAFTextView;
    private TextView onePaySumTextView;
    private TextView oneInterestTextView;
    private TextView twoLoanSumTextView;                //显示等额本金的结果
    private TextView twoMonthTextView;
    private TextView twoPaySumCommTextView;
    private TextView twoInterestCommTextView;
    private TextView twoPaySumHAFTextView;
    private TextView twoInterestHAFTextView;
    private TextView twoPaySumTextView;
    private TextView twoInterestTextView;
    //等额本息的结果数据
    private String oneSumCommString;                    //商业还款
    private String oneInterestCommString;               //商业利息
    private String oneSumHAFString;                     //公积金还款
    private String oneInterestHAFString;                //公积金利息
    private String onePayString;                        //还款总额
    private String oneInterestString;                   //利息总额
    private String[] oneTimeStrings;
    private String[] oneCapitalStrings;
    private String[] oneInterestStrings;
    private String[] oneMonthPayStrings;
    //等额本金的结果数据
    private String twoSumCommString;                    //商业还款
    private String twoInterestCommString;               //商业利息
    private String twoSumHAFString;                     //公积金还款
    private String twoInterestHAFString;                //公积金利息
    private String twoPayString;                        //还款总额
    private String twoInterestString;                   //利息总额
    private String[] twoTimeStrings;
    private String[] twoCapitalStrings;
    private String[] twoInterestStrings;
    private String[] twoMonthPayStrings;
    private int currentItem = 0;
    private ImageView cursorImageView;
    private int offSet;
    private final Matrix matrix = new Matrix();
    private Animation animation;
    private ProgressDialog progressDialog = null;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DONE:
                    showResult();                       //9.显示结果

                    //等额本息的结果
                    ResultListViewAdapter adapterList1 = new ResultListViewAdapter(ResultCombinationActivity.this, oneTimeStrings, oneCapitalStrings, oneInterestStrings, oneMonthPayStrings);
                    listViewOne.setAdapter(adapterList1);

                    //等额本金的结果
                    ResultListViewAdapter adapterList2 = new ResultListViewAdapter(ResultCombinationActivity.this, twoTimeStrings, twoCapitalStrings, twoInterestStrings, twoMonthPayStrings);
                    listViewTwo.setAdapter(adapterList2);

                    viewPager.setCurrentItem(currentItem);

                    progressDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_combination);

        progressDialog = ProgressDialog.show(ResultCombinationActivity.this, "", "正在计算...", false, true);

        init();                     //0.初始化
        getData();                  //1.获得数据
        initViews();                //2.初始化控件
        initViewPager();            //3.设置ViewPager
        setListeners();             //4.设置监听器

        //启动新线程来计算
        new Thread(new Runnable() {
            @Override
            public void run() {
                calculateTypeOne(time, aheadTime);              //5.等额本息的计算方法
                sortOneStrings();                               //7.等额本金数据整理

                calculateTypeTwo(time, aheadTime);              //6.等额本金的计算方法
                sortTwoStrings();                               //8.等额本金数据整理

                handler.sendEmptyMessage(DONE);
            }
        }).start();
    }

    //0.初始化
    public void init() {
    }

    //1.获得数据
    public void getData() {
        //从前一个Activity传来的数据
        Bundle bundle = this.getIntent().getExtras();
        String mortgageString = bundle.getString("mortgage");                   //贷款总额
        String HAFMortgageString = bundle.getString("HAFMortgage");             //公积金贷款
        String commMortgageString = bundle.getString("commMortgage");           //商业贷款
        String timeString = bundle.getString("time");
        String HAFRateString = bundle.getString("HAFRate");                     //公积金利率
        String commRateString = bundle.getString("commRate");                   //商业利率
        String a = bundle.getString("aheadTime");
        firstYear = bundle.getInt("firstYear");
        firstMonth = bundle.getInt("firstMonth");
        currentItem = bundle.getInt("paybackMethod");

        this.setTitle("组合贷款");

        //万元转换为元
        mortgage = Double.valueOf(mortgageString);
        mortgage = mortgage * 10000;

        HAFMortgage = Double.valueOf(HAFMortgageString);
        HAFMortgage = HAFMortgage * 10000;

        commMortgage = Double.valueOf(commMortgageString);
        commMortgage = commMortgage * 10000;

        //年利率转换为月利率
        HAFRate = Double.valueOf(HAFRateString);
        HAFRate = HAFRate / 100;
        HAFMonthRate = HAFRate / 12;

        commRate = Double.valueOf(commRateString);
        commRate = commRate / 100;
        commMonthRate = commRate / 12;

        //贷款时间转换为月
        time = Integer.valueOf(timeString);
        time = time * 12;

        //第几年还款转换为月
        aheadTime = Integer.valueOf(a);
        aheadTime = aheadTime * 12;
    }

    //2.初始化控件
    public void initViews() {
        viewPager = findViewById(R.id.Result_Combination_Viewpager);
        typeOneText = findViewById(R.id.Result_Combination_TypeOne_TextView);
        typeTwoText = findViewById(R.id.Result_Combination_TypeTwo_TextView);
        cursorImageView = findViewById(R.id.Result_Combination_Cursor_ImageView);
    }

    //3.设置ViewPager
    public void initViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater().from(this);

        view1 = layoutInflater.inflate(R.layout.viewpager_capital_interest_combination, null);
        view2 = layoutInflater.inflate(R.layout.viewpager_capital_combination, null);

        viewList.add(view1);
        viewList.add(view2);

        oneLoanSumTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_LoanSum_Number_TextView);
        oneMonthTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_Month_Number_TextView);
        onePaySumCommTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_CommPaySum_Number_TextView);
        oneInterestCommTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_CommInterest_Number_TextView);
        onePaySumHAFTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_HAFPaySum_Number_TextView);
        oneInterestHAFTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_HAFInterest_Number_TextView);
        onePaySumTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_PaySum_Number_TextView);
        oneInterestTextView = view1.findViewById(R.id.ViewPager_CapitalInterestCombination_Interest_Number_TextView);
        listViewOne = view1.findViewById(R.id.CapitalInterestCombination_ListOne);

        twoLoanSumTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_LoanSum_Number_TextView);
        twoMonthTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_Month_Number_TextView);
        twoPaySumCommTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_CommPaySum_Number_TextView);
        twoInterestCommTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_CommInterest_Number_TextView);
        twoPaySumHAFTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_HAFPaySum_Number_TextView);
        twoInterestHAFTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_HAFInterest_Number_TextView);
        twoPaySumTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_PaySum_Number_TextView);
        twoInterestTextView = view2.findViewById(R.id.ViewPager_CapitalCombination_Interest_Number_TextView);
        listViewTwo = view2.findViewById(R.id.CapitalCombination_ListTwo);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new PageChangeListener());        //3-1.ViewPager的监听器

        //设置光标
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        offSet = displayMetrics.widthPixels / 2;                            //每个标题的宽度（720/2=360）
        matrix.setTranslate(0, 0);
        cursorImageView.setImageMatrix(matrix);
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
    public void calculateTypeOne(int time, int aheadTime) {
        //如果没有提前还款
        if (aheadTime == 0) {
            aheadTime = time;
        }

        oneTimeStrings = new String[aheadTime + 1];
        oneCapitalStrings = new String[aheadTime + 1];
        oneInterestStrings = new String[aheadTime + 1];
        oneMonthPayStrings = new String[aheadTime + 1];

        double[] monthCommCapital = new double[time + 1];
        double[] monthCommInterest = new double[time + 1];
        double[] monthCommSum = new double[time + 1];
        double[] monthHAFCapital = new double[time + 1];
        double[] monthHAFInterest = new double[time + 1];
        double[] monthHAFSum = new double[time + 1];

        double paidCommCapital = 0;     //已还本金
        double paidCommInterest = 0;    //已还利息
        double paidComm = 0;            //总共已还
        double paidHAFCapital = 0;      //已还本金
        double paidHAFInterest = 0;     //已还利息
        double paidHAF = 0;             //总共已还

        DecimalFormat df = new DecimalFormat("#,###.0");       //保留两位小数

        for (int i = 1; i <= aheadTime; i++) {
            monthCommCapital[i] = commMortgage * commMonthRate * Math.pow((1 + commMonthRate), i - 1) / (Math.pow(1 + commMonthRate, time) - 1);
            monthCommInterest[i] = commMortgage * commMonthRate * (Math.pow(1 + commMonthRate, time) - Math.pow(1 + commMonthRate, i - 1)) / (Math.pow(1 + commMonthRate, time) - 1);
            monthCommSum[i] = commMortgage * commMonthRate * Math.pow((1 + commMonthRate), time) / (Math.pow(1 + commMonthRate, time) - 1);

            monthHAFCapital[i] = HAFMortgage * HAFMonthRate * Math.pow((1 + HAFMonthRate), i - 1) / (Math.pow(1 + HAFMonthRate, time) - 1);
            monthHAFInterest[i] = HAFMortgage * HAFMonthRate * (Math.pow(1 + HAFMonthRate, time) - Math.pow(1 + HAFMonthRate, i - 1)) / (Math.pow(1 + HAFMonthRate, time) - 1);
            monthHAFSum[i] = HAFMortgage * HAFMonthRate * Math.pow((1 + HAFMonthRate), time) / (Math.pow(1 + HAFMonthRate, time) - 1);

            oneTimeStrings[i] = i + "期";

            oneCapitalStrings[i] = df.format(monthCommCapital[i] + monthHAFCapital[i]);
            oneInterestStrings[i] = df.format(monthCommInterest[i] + monthHAFInterest[i]);
            oneMonthPayStrings[i] = df.format(monthCommSum[i] + monthHAFSum[i]);

            paidCommCapital = paidCommCapital + commMortgage * commMonthRate * Math.pow((1 + commMonthRate), i - 1) / (Math.pow(1 + commMonthRate, time) - 1);
            paidCommInterest = paidCommInterest + commMortgage * commMonthRate * (Math.pow(1 + commMonthRate, time) - Math.pow(1 + commMonthRate, i - 1)) / (Math.pow(1 + commMonthRate, time) - 1);
            paidComm = paidComm + commMortgage * commMonthRate * Math.pow((1 + commMonthRate), time) / (Math.pow(1 + commMonthRate, time) - 1);

            paidHAFCapital = paidHAFCapital + HAFMortgage * HAFMonthRate * Math.pow((1 + HAFMonthRate), i - 1) / (Math.pow(1 + HAFMonthRate, time) - 1);
            paidHAFInterest = paidHAFInterest + HAFMortgage * HAFMonthRate * (Math.pow(1 + HAFMonthRate, time) - Math.pow(1 + HAFMonthRate, i - 1)) / (Math.pow(1 + HAFMonthRate, time) - 1);
            paidHAF = paidHAF + HAFMortgage * HAFMonthRate * Math.pow((1 + HAFMonthRate), time) / (Math.pow(1 + HAFMonthRate, time) - 1);

        }

        double monthPayComm = commMortgage * commMonthRate * Math.pow((1 + commMonthRate), time) / (Math.pow(1 + commMonthRate, time) - 1);
        double sumComm = monthPayComm * time;
        double interestComm = monthPayComm * time - commMortgage;
        oneSumCommString = df.format(sumComm);
        oneInterestCommString = df.format(interestComm);

        double monthPayHAF = HAFMortgage * HAFMonthRate * Math.pow((1 + HAFMonthRate), time) / (Math.pow(1 + HAFMonthRate, time) - 1);
        double sumHAF = monthPayHAF * time;
        double interestHAF = monthPayHAF * time - HAFMortgage;
        oneSumHAFString = df.format(sumHAF);
        oneInterestHAFString = df.format(interestHAF);

        double onePay = (sumComm) + (sumHAF);
        onePayString = df.format(onePay);

        double oneInterest = (interestComm) + (interestHAF);
        oneInterestString = df.format(oneInterest);
    }

    //6.等额本金的计算方法
    public void calculateTypeTwo(int time, int aheadTime) {
        if (aheadTime == 0) {
            aheadTime = time;
        }

        twoTimeStrings = new String[aheadTime + 1];
        twoCapitalStrings = new String[aheadTime + 1];
        twoInterestStrings = new String[aheadTime + 1];
        twoMonthPayStrings = new String[aheadTime + 1];

        double[] monthCommCapital = new double[time + 1];
        double[] monthCommInterest = new double[time + 1];
        double[] monthCommSum = new double[time + 1];
        double[] monthHAFCapital = new double[time + 1];
        double[] monthHAFInterest = new double[time + 1];
        double[] monthHAFSum = new double[time + 1];

        double paidComm = 0;
        double paidCommCapital = 0;
        double paidCommInterest = 0;
        double paidCommSum = 0;
        double paidHAF = 0;
        double paidHAFCapital = 0;
        double paidHAFInterest = 0;
        double paidHAFSum = 0;

        DecimalFormat df = new DecimalFormat("#,###.0");

        for (int i = 1; i <= aheadTime; i++) {
            monthCommCapital[i] = commMortgage / time;
            monthCommInterest[i] = (commMortgage - paidComm) * commMonthRate;
            monthCommSum[i] = (commMortgage / time) + (commMortgage - paidComm) * commMonthRate;
            paidComm = paidComm + commMortgage / time;
            paidCommCapital = paidCommCapital + commMortgage / time;
            paidCommInterest = paidCommInterest + (commMortgage - paidComm) * commMonthRate;
            paidCommSum = paidCommSum + (commMortgage / time) + (commMortgage - paidComm) * commMonthRate;

            monthHAFCapital[i] = HAFMortgage / time;
            monthHAFInterest[i] = (HAFMortgage - paidHAF) * HAFMonthRate;
            monthHAFSum[i] = (HAFMortgage / time) + (HAFMortgage - paidHAF) * HAFMonthRate;
            paidHAF = paidHAF + HAFMortgage / time;
            paidHAFCapital = paidHAFCapital + HAFMortgage / time;
            paidHAFInterest = paidHAFInterest + (HAFMortgage - paidHAF) * HAFMonthRate;
            paidHAFSum = paidHAFSum + (HAFMortgage / time) + (HAFMortgage - paidComm) * HAFMonthRate;

            twoTimeStrings[i] = i + "期";

            twoCapitalStrings[i] = df.format(monthCommCapital[i] + monthHAFCapital[i]);
            twoInterestStrings[i] = df.format(monthCommInterest[i] + monthHAFInterest[i]);
            twoMonthPayStrings[i] = df.format(monthCommSum[i] + monthHAFSum[i]);
        }
        double sumComm = time * (commMortgage * commMonthRate - commMonthRate * (commMortgage / time) * (time - 1) / 2 + commMortgage / time);
        double interestComm = sumComm - commMortgage;

        double sumHAF = time * (HAFMortgage * HAFMonthRate - HAFMonthRate * (HAFMortgage / time) * (time - 1) / 2 + HAFMortgage / time);
        double interestHAF = sumHAF - HAFMortgage;

        twoSumCommString = df.format(sumComm);
        twoInterestCommString = df.format(interestComm);
        twoSumHAFString = df.format(sumHAF);
        twoInterestHAFString = df.format(interestHAF);

        double twoPay = sumComm + sumHAF;
        twoPayString = df.format(twoPay);

        double twoInterest = interestComm + interestHAF;
        twoInterestString = df.format(twoInterest);
    }

    //7.等额本息数据整理
    public void sortOneStrings() {
        ArrayList timeList = new ArrayList();
        ArrayList capitalList = new ArrayList();
        ArrayList interestList = new ArrayList();
        ArrayList monthPayList = new ArrayList();

        int deltaMonth = 12 - firstMonth + 1;
        int deltaYear = time / 12;
        int max = 0;

        //开始月份不是1月
        if (deltaMonth != 12) {
            String[] years = new String[deltaYear + 1];
            for (int i = 0; i < deltaYear + 1; i++) {
                years[i] = (firstYear + i) + "年";
            }
            max = time + (deltaYear + 1) - (deltaMonth + 1);

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
            max = time + deltaYear;
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
    }

    //8.等额本金数据整理
    public void sortTwoStrings() {
        ArrayList timeList = new ArrayList();
        ArrayList capitalList = new ArrayList();
        ArrayList interestList = new ArrayList();
        ArrayList monthPayList = new ArrayList();

        int deltaMonth = 12 - firstMonth + 1;
        int deltaYear = time / 12;
        int max = 0;

        //开始月份不是1月
        if (deltaMonth != 12) {
            String[] years = new String[deltaYear + 1];
            for (int i = 0; i < deltaYear + 1; i++) {
                years[i] = (firstYear + i) + "年";
            }

            max = time + (deltaYear + 1) - (deltaMonth + 1);
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
            max = time + deltaYear;
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
    }

    //9.显示结果
    public void showResult() {
        DecimalFormat df = new DecimalFormat("#,###.0");

        if (aheadTime == 0) {
            //等额本息的结果
            oneLoanSumTextView.setText(df.format(mortgage / 10000) + "万元");
            oneMonthTextView.setText(time + "月");
            onePaySumCommTextView.setText(oneSumCommString + "元");
            oneInterestCommTextView.setText(oneInterestCommString + "元");
            onePaySumHAFTextView.setText(oneSumHAFString + "元");
            oneInterestHAFTextView.setText(oneInterestHAFString + "元");
            onePaySumTextView.setText(onePayString + "元");
            oneInterestTextView.setText(oneInterestString + "元");

            //等额本金的结果
            twoLoanSumTextView.setText(df.format(mortgage / 10000) + "万元");
            twoMonthTextView.setText(time + "月");
            twoPaySumCommTextView.setText(twoSumCommString + "元");
            twoInterestCommTextView.setText(twoInterestCommString + "元");
            twoPaySumHAFTextView.setText(twoSumHAFString + "元");
            twoInterestHAFTextView.setText(twoInterestHAFString + "元");
            twoPaySumTextView.setText(twoPayString + "元");
            twoInterestTextView.setText(twoInterestString + "元");
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
            ResultCombinationActivity.this.finish();
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
