package edu.jiangxin.droiddemo.tools.mortgagecalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.jiangxin.droiddemo.R;

public class MainActivity extends Activity {
    //动态添加的控件
    private final static int COMM_CAPITAL = 0;
    private final static int COMM_AREA = 1;
    //动态添加的控件
    private final static int HAF_CAPITAL = 0;
    private final static int HAF_AREA = 1;
    //动态添加的控件
    private final static int COMB_CAPITAL = 0;
    private final static int COMB_AREA = 1;
    private TextView commercialLoanTextView;        //商业贷款
    private TextView HAFTextView;                   //公积金贷款
    private TextView combinationTextView;           //组合贷款
    private ViewPager viewPager;
    private List<View> viewList;
    private View commercialLoanView;
    private View HAFView;
    private View combinationView;
    private int currentItem = 0;
    private ImageView cursorImageView;
    private int offSet;
    private final Matrix matrix = new Matrix();
    private Animation animation;
    private Button resetButton;
    private Button calculationButton;
    //商业贷款部分
    private Spinner CommPaybackMethodSpinner;
    private Spinner CommTimeSpinner;
    private Spinner CommCalculationMethodSpinner;
    private EditText CommRateEditText;
    private EditText CommDiscountEditText;
    private TextView CommRateTextView;
    private TextView CommFirstTimeTextView;
    private Calendar CommCalendar;
    private DatePickerDialog CommDatePickerDialog;
    private int CommCalculationMethod = COMM_CAPITAL;
    private LinearLayout CommMortgageDynamicLayout;
    private LinearLayout CommAreaDynamicLayout;
    private EditText CommMortgageEditText;          //按贷款金额计算
    private EditText CommPriceEditText;             //按面积计算
    private EditText CommAreaEditText;
    private EditText CommAreaSumEditText;
    private Spinner CommFirstPaySpinner;
    private EditText CommFirstPayEditText;
    private EditText CommPayEditText;
    private String CommTime;                        //贷款时间（年）
    private String CommSum;                         //总价
    private String CommMortgage;                    //贷款金额（万元）
    private String CommFirstPayPercent;             //首付比例
    private String CommFirstPay;                    //首付金额（万元）
    private String CommRate;                        //贷款年利率（%）
    private int CommFirstYear;                      //首次还款年月
    private int CommFirstMonth;
    private int CommPaybackMethod;                  //还款方式（等额本息、等额本金）
    //公积金贷款部分
    private Spinner HAFPaybackMethodSpinner;
    private Spinner HAFTimeSpinner;
    private Spinner HAFCalculationMethodSpinner;
    private EditText HAFRateEditText;
    private EditText HAFDiscountEditText;
    private TextView HAFRateTextView;
    private TextView HAFFirstTimeTextView;
    private Calendar HAFCalendar;
    private DatePickerDialog HAFDatePickerDialog;
    private int HAFCalculationMethod = HAF_CAPITAL;
    private LinearLayout HAFMortgageDynamicLayout;
    private LinearLayout HAFAreaDynamicLayout;
    private EditText HAFMortgageEditText;           //按贷款金额计算
    private EditText HAFPriceEditText;              //按面积计算
    private EditText HAFAreaEditText;
    private EditText HAFAreaSumEditText;
    private Spinner HAFFirstPaySpinner;
    private EditText HAFFirstPayEditText;
    private EditText HAFPayEditText;
    private String HAFTime;                        //贷款时间（年）
    private String HAFSum;                         //总价
    private String HAFMortgage;                    //贷款金额（万元）
    private String HAFFirstPayPercent;             //首付比例
    private String HAFFirstPay;                    //首付金额（万元）
    private String HAFRate;                        //贷款年利率（%）
    private int HAFFirstYear;                      //首次还款年月
    private int HAFFirstMonth;
    private int HAFPaybackMethod;                  //还款方式（等额本息、等额本金）
    //组合贷款部分
    private Spinner CombPaybackMethodSpinner;
    private Spinner CombTimeSpinner;
    private Spinner CombCalculationMethodSpinner;
    private EditText CombCommRateEditText;                  //商业贷款利率
    private EditText CombCommDiscountEditText;
    private TextView CombCommRateTextView;
    private EditText CombHAFRateEditText;                   //公积金贷款利率
    private EditText CombHAFDiscountEditText;
    private TextView CombHAFRateTextView;
    private TextView CombFirstTimeTextView;
    private Calendar CombCalendar;
    private DatePickerDialog CombDatePickerDialog;
    private int CombCalculationMethod = COMB_CAPITAL;
    private LinearLayout CombMortgageDynamicLayout;
    private LinearLayout CombAreaDynamicLayout;
    //按贷款金额计算
    private EditText CombMortgageHAFEditText;                       //公积金贷款部分
    private EditText CombMortgageCommEditText;                      //商业贷款部分
    //按面积计算
    private EditText CombAreaPriceEditText;                         //单价
    private EditText CombAreaAreaEditText;                          //面积
    private EditText CombAreaSumEditText;                           //总价
    private Spinner CombAreaFirstPaySpinner;                        //首付比例
    private EditText CombAreaFirstPayEditText;                      //首付
    private EditText CombAreaPayEditText;                           //贷款总额
    private EditText CombAreaHAFPayEditText;                        //公积金贷款金额
    private EditText CombAreaCommPayEditText;                       //商业贷款金额

    private String CombTime;                        //贷款时间（年）
    private String CombSum;                         //总价
    private String CombFirstPayPercent;             //首付比例
    private String CombFirstPay;                    //首付金额（万元）
    private String CombPay;                         //贷款总额（万元）
    private String CombMortgageComm;                //商业贷款金额（万元）
    private String CombMortgageHAF;                 //公积金贷款金额（万元）
    private String CombHAFRate;                     //公积金贷款年利率（%）
    private String CombCommRate;                    //商业贷款年利率（%）
    private int CombFirstYear;                      //首次还款年月
    private int CombFirstMonth;
    private int CombPaybackMethod;                  //还款方式（等额本息、等额本金）

    //程序退出时的时间
    private final long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);

        init();                             //0.初始化
        initViews();                        //1.初始化Views
        initViewPager();                    //2.初始化ViewPager
        setListeners();                     //3.设置监听器
        setSpinner();                       //4.设置Spinner
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

    //1.初始化Views
    public void initViews() {
        commercialLoanTextView = findViewById(R.id.CommercialLoanTextView);
        HAFTextView = findViewById(R.id.HAFTextView);
        combinationTextView = findViewById(R.id.CombinationTextView);
        viewPager = findViewById(R.id.viewpager);
        cursorImageView = findViewById(R.id.cursorImageView);
        resetButton = findViewById(R.id.resetButton);
        calculationButton = findViewById(R.id.calculationButton);

        CommCalendar = Calendar.getInstance();
        CommFirstYear = CommCalendar.get(Calendar.YEAR);
        CommFirstMonth = CommCalendar.get(Calendar.MONTH) + 1;

        HAFCalendar = Calendar.getInstance();
        HAFFirstYear = HAFCalendar.get(Calendar.YEAR);
        HAFFirstMonth = HAFCalendar.get(Calendar.MONTH) + 1;

        CombCalendar = Calendar.getInstance();
        CombFirstYear = CombCalendar.get(Calendar.YEAR);
        CombFirstMonth = CombCalendar.get(Calendar.MONTH) + 1;
    }

    //2.初始化ViewPager
    public void initViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater().from(this);

        //三个标题
        commercialLoanView = layoutInflater.inflate(R.layout.viewpager_commercialloan, null);
        HAFView = layoutInflater.inflate(R.layout.viewpager_haf, null);
        combinationView = layoutInflater.inflate(R.layout.viewpager_combination, null);

        //商业贷款页面控件
        CommPaybackMethodSpinner = commercialLoanView.findViewById(R.id.Commercial_PaybackMethod_Spinner);
        CommCalculationMethodSpinner = commercialLoanView.findViewById(R.id.Commercial_CalculationMethod_Spinner);
        CommTimeSpinner = commercialLoanView.findViewById(R.id.Commercial_TimeSpinner);
        CommRateEditText = commercialLoanView.findViewById(R.id.Commercial_RateEditText);
        CommDiscountEditText = commercialLoanView.findViewById(R.id.Commercial_DiscountEditText);
        CommRateTextView = commercialLoanView.findViewById(R.id.Commercial_RateTextView);
        CommFirstTimeTextView = commercialLoanView.findViewById(R.id.Commercial_FirstTimePickTextView);
        CommFirstTimeTextView.setText(CommFirstYear + " 年" + CommFirstMonth + " 月");
        //动态添加
        CommMortgageDynamicLayout = commercialLoanView.findViewById(R.id.Commercial_Dynamic_Mortgage_Layout);
        CommAreaDynamicLayout = commercialLoanView.findViewById(R.id.Commercial_Dynamic_Area_Layout);
        CommMortgageEditText = commercialLoanView.findViewById(R.id.Commercial_MortgageEditText);
        CommPriceEditText = commercialLoanView.findViewById(R.id.Commercial_Price_EditText);
        CommAreaEditText = commercialLoanView.findViewById(R.id.Commercial_Area_EditText);
        CommAreaSumEditText = commercialLoanView.findViewById(R.id.Commercial_Area_Sum_EditText);
        CommFirstPaySpinner = commercialLoanView.findViewById(R.id.Commercial_FirstPay_Spinner);
        CommFirstPayEditText = commercialLoanView.findViewById(R.id.Commercial_FirstPay_EditText);
        CommPayEditText = commercialLoanView.findViewById(R.id.Commercial_Pay_EditText);

        //公积金贷款页面
        HAFPaybackMethodSpinner = HAFView.findViewById(R.id.HAF_PaybackMethod_Spinner);
        HAFCalculationMethodSpinner = HAFView.findViewById(R.id.HAF_CalculationMethod_Spinner);
        HAFTimeSpinner = HAFView.findViewById(R.id.HAF_TimeSpinner);
        HAFRateEditText = HAFView.findViewById(R.id.HAF_RateEditText);
        HAFDiscountEditText = HAFView.findViewById(R.id.HAF_DiscountEditText);
        HAFRateTextView = HAFView.findViewById(R.id.HAF_RateTextView);
        HAFFirstTimeTextView = HAFView.findViewById(R.id.HAF_FirstTimePickTextView);
        HAFFirstTimeTextView.setText(HAFFirstYear + " 年" + HAFFirstMonth + " 月");
        //动态添加
        HAFMortgageDynamicLayout = HAFView.findViewById(R.id.HAF_Dynamic_Mortgage_Layout);
        HAFAreaDynamicLayout = HAFView.findViewById(R.id.HAF_Dynamic_Area_Layout);
        HAFMortgageEditText = HAFView.findViewById(R.id.HAF_MortgageEditText);
        HAFPriceEditText = HAFView.findViewById(R.id.HAF_Price_EditText);
        HAFAreaEditText = HAFView.findViewById(R.id.HAF_Area_EditText);
        HAFAreaSumEditText = HAFView.findViewById(R.id.HAF_Area_Sum_EditText);
        HAFFirstPaySpinner = HAFView.findViewById(R.id.HAF_FirstPay_Spinner);
        HAFFirstPayEditText = HAFView.findViewById(R.id.HAF_FirstPay_EditText);
        HAFPayEditText = HAFView.findViewById(R.id.HAF_Pay_EditText);

        //组合贷款页面
        CombPaybackMethodSpinner = combinationView.findViewById(R.id.Combination_PaybackMethod_Spinner);
        CombTimeSpinner = combinationView.findViewById(R.id.Combination_Time_Spinner);
        CombCalculationMethodSpinner = combinationView.findViewById(R.id.Combination_CalculationMethod_Spinner);
        CombHAFRateEditText = combinationView.findViewById(R.id.Combination_HAF_Rate_EditText);
        CombHAFDiscountEditText = combinationView.findViewById(R.id.Combination_HAF_Discount_EditText);
        CombHAFRateTextView = combinationView.findViewById(R.id.Combination_HAF_Rate_TextView);
        CombCommRateEditText = combinationView.findViewById(R.id.Combination_Mortgage_Rate_EditText);
        CombCommDiscountEditText = combinationView.findViewById(R.id.Combination_Mortgage_Discount_EditText);
        CombCommRateTextView = combinationView.findViewById(R.id.Combination_Mortgage_Rate_TextView);
        CombFirstTimeTextView = combinationView.findViewById(R.id.Combination_FirstTimePickTextView);
        CombFirstTimeTextView.setText(CombFirstYear + " 年" + CombFirstMonth + " 月");
        //动态添加
        CombMortgageDynamicLayout = combinationView.findViewById(R.id.Combination_Dynamic_Mortgage_Layout);
        CombAreaDynamicLayout = combinationView.findViewById(R.id.Combination_Dynamic_Area_Layout);
        CombMortgageHAFEditText = combinationView.findViewById(R.id.Combination_Mortgage_HAF_EditText);
        CombMortgageCommEditText = combinationView.findViewById(R.id.Combination_Mortgage_Comm_EditText);
        CombAreaPriceEditText = combinationView.findViewById(R.id.Combination_Area_Price_EditText);
        CombAreaAreaEditText = combinationView.findViewById(R.id.Combination_Area_Area_EditText);
        CombAreaSumEditText = combinationView.findViewById(R.id.Combination_Area_Sum_EditText);
        CombAreaFirstPaySpinner = combinationView.findViewById(R.id.Combination_Area_FirstPay_Spinner);
        CombAreaFirstPayEditText = combinationView.findViewById(R.id.Combination_Area_FirstPay_EditText);
        CombAreaPayEditText = combinationView.findViewById(R.id.Combination_Area_Pay_EditText);
        CombAreaHAFPayEditText = combinationView.findViewById(R.id.Combination_Area_HAFPay_EditText);
        CombAreaCommPayEditText = combinationView.findViewById(R.id.Combination_Area_CommPay_EditText);

        viewList.add(commercialLoanView);
        viewList.add(HAFView);
        viewList.add(combinationView);

        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.addOnPageChangeListener(new PageChangeListener());

        //设置光标
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        offSet = displayMetrics.widthPixels / 3;         //每个标题的宽度（720/3=240）
        matrix.setTranslate(0, 0);
        cursorImageView.setImageMatrix(matrix);          // 需要imageView的scaleType为matrix*/
    }

    //3.设置监听器
    public void setListeners() {
        //ViewPager三个标题
        commercialLoanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        HAFTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        combinationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        //贷款金额清空
        CommMortgageEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommMortgageEditText.setText("");
                return false;
            }
        });

        HAFMortgageEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HAFMortgageEditText.setText("");
                return false;
            }
        });

        CombMortgageCommEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombMortgageCommEditText.setText("");
                return false;
            }
        });

        CombMortgageHAFEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombMortgageHAFEditText.setText("");
                return false;
            }
        });

        //贷款金额检查
        CommMortgageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMM_CAPITAL);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        HAFMortgageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(HAF_CAPITAL);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        CombMortgageHAFEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMB_CAPITAL);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        CombMortgageCommEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMB_CAPITAL);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        //单价清空
        CommPriceEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommPriceEditText.setText("");
                return false;
            }
        });

        HAFPriceEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HAFPriceEditText.setText("");
                return false;
            }
        });

        CombAreaPriceEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombAreaPriceEditText.setText("");
                return false;
            }
        });

        //单价检查并计算
        CommPriceEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMM_AREA);                           //3-1.检查是否都填写了
                    setFistPay();                                       //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        HAFPriceEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(HAF_AREA);                            //3-1.检查是否都填写了
                    setFistPay();                                       //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        CombAreaPriceEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMB_AREA);                            //3-1.检查是否都填写了
                    setFistPay();                                       //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        //面积清空
        CommAreaEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommAreaEditText.setText("");
                return false;
            }
        });

        HAFAreaEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HAFAreaEditText.setText("");
                return false;
            }
        });

        CombAreaAreaEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombAreaAreaEditText.setText("");
                return false;
            }
        });

        //面积检查并计算
        CommAreaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMM_AREA);                           //3-1.检查是否都填写了
                    setFistPay();                                       //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        HAFAreaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(HAF_AREA);                            //3-1.检查是否都填写了
                    setFistPay();                                       //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        CombAreaAreaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMB_AREA);                            //3-1.检查是否都填写了
                    setFistPay();                                        //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        //组合贷款中公积金贷款的金额EditText
        CombAreaHAFPayEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombAreaHAFPayEditText.setText("");
                return false;
            }
        });

        CombAreaHAFPayEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(COMB_AREA);                            //3-1.检查是否都填写了
                    setFistPay();                                        //3-3.计算首付和贷款金额
                }
                return false;
            }
        });

        //利率清空
        CommRateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommRateEditText.setText("");
                return false;
            }
        });

        HAFRateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HAFRateEditText.setText("");
                return false;
            }
        });

        CombHAFRateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombHAFRateEditText.setText("");
                return false;
            }
        });

        CombCommRateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombCommRateEditText.setText("");
                return false;
            }
        });

        //利率检查并计算
        CommRateEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CommCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        HAFRateEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(HAFCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        CombHAFRateEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CombCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        CombCommRateEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CombCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        //折扣（倍数）清空
        CommDiscountEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommDiscountEditText.setText("");
                return false;
            }
        });
        CommDiscountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utils.isDoubleOrFloat(s)) {
                    setRateTextView();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        HAFDiscountEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HAFDiscountEditText.setText("");
                return false;
            }
        });
        HAFDiscountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utils.isDoubleOrFloat(s)) {
                    setRateTextView();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        CombHAFDiscountEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombHAFDiscountEditText.setText("");
                return false;
            }
        });

        CombCommDiscountEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CombCommDiscountEditText.setText("");
                return false;
            }
        });

        //折扣（倍数）检查并计算
        CommDiscountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CommCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        HAFDiscountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CommCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        CombHAFDiscountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CombCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        CombCommDiscountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkValidity(CombCalculationMethod);                        //3-1.检查是否都填写了
                }
                return false;
            }
        });

        //首次还款时间监听器，弹出日期选择框
        CommFirstTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommDatePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        CommFirstTimeTextView.setText(year + " 年" + (monthOfYear + 1) + " 月");
                        CommFirstYear = year;
                        CommFirstMonth = monthOfYear + 1;
                    }
                }, CommCalendar.get(Calendar.YEAR), CommCalendar.get(Calendar.MONTH), CommCalendar.get(Calendar.DAY_OF_MONTH));
                CommDatePickerDialog.show();
            }
        });

        HAFFirstTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HAFDatePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        HAFFirstTimeTextView.setText(year + " 年" + (monthOfYear + 1) + " 月");
                        HAFFirstYear = year;
                        HAFFirstMonth = monthOfYear + 1;
                    }
                }, HAFCalendar.get(Calendar.YEAR), HAFCalendar.get(Calendar.MONTH), HAFCalendar.get(Calendar.DAY_OF_MONTH));
                HAFDatePickerDialog.show();
            }
        });

        CombFirstTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CombDatePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        CombFirstTimeTextView.setText(year + " 年" + (monthOfYear + 1) + " 月");
                        CombFirstYear = year;
                        CombFirstMonth = monthOfYear + 1;
                    }
                }, CombCalendar.get(Calendar.YEAR), CombCalendar.get(Calendar.MONTH), CombCalendar.get(Calendar.DAY_OF_MONTH));
                CombDatePickerDialog.show();
            }
        });

        //重置按钮
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "0.00";
                switch (currentItem) {
                    case 0:
                        CommMortgageEditText.setText(string);
                        CommPriceEditText.setText(string);
                        CommAreaEditText.setText(string);
                        CommAreaSumEditText.setText(string);
                        CommFirstPayEditText.setText(string);
                        CommPayEditText.setText(string);
                        CommRateEditText.setText("4.9");
                        CommDiscountEditText.setText("1");
                        CommRateTextView.setText("4.900%");
                        CommFirstPaySpinner.setSelection(1, true);
                        break;
                    case 1:
                        HAFMortgageEditText.setText(string);
                        HAFPriceEditText.setText(string);
                        HAFAreaEditText.setText(string);
                        HAFAreaSumEditText.setText(string);
                        HAFFirstPayEditText.setText(string);
                        HAFPayEditText.setText(string);
                        HAFRateEditText.setText("3.25");
                        HAFDiscountEditText.setText("1");
                        HAFRateTextView.setText("3.250%");
                        HAFFirstPaySpinner.setSelection(1, true);
                        break;
                    case 2:
                        CombMortgageHAFEditText.setText(string);
                        CombMortgageCommEditText.setText(string);
                        CombAreaPriceEditText.setText(string);
                        CombAreaAreaEditText.setText(string);
                        CombAreaSumEditText.setText(string);
                        CombAreaFirstPayEditText.setText(string);
                        CombAreaPayEditText.setText(string);
                        CombAreaHAFPayEditText.setText(string);
                        CombAreaCommPayEditText.setText(string);
                        CombHAFRateEditText.setText("3.25");
                        CombHAFDiscountEditText.setText("1");
                        CombHAFRateTextView.setText("3.250%");
                        CombCommRateEditText.setText("4.9");
                        CombCommDiscountEditText.setText("1");
                        CombCommRateTextView.setText("4.900%");
                        CombAreaFirstPaySpinner.setSelection(1, true);
                        break;
                }
            }
        });

        //计算按钮
        calculationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                switch (currentItem) {
                    case 0:
                        //按贷款金额计算
                        if (CommCalculationMethod == COMM_CAPITAL) {
                            CommMortgage = CommMortgageEditText.getText().toString();
                        }
                        //按住房面积计算
                        if (CommCalculationMethod == COMM_AREA) {
                            CommMortgage = CommPayEditText.getText().toString();
                        }
                        CommRate = CommRateTextView.getText().toString().substring(0, 5);

                        if (Double.valueOf(CommMortgage) == 0) {
                            builder.setMessage("贷款金额不能为0");
                            builder.setTitle("提示");
                            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                            break;
                        }

                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, ResultActivity.class);      //跳转到结果列表
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mortgage", CommMortgage);
                        bundle1.putString("time", CommTime);
                        bundle1.putString("rate", CommRate);
                        bundle1.putString("aheadTime", "0");
                        bundle1.putInt("firstYear", CommFirstYear);
                        bundle1.putInt("firstMonth", CommFirstMonth);
                        bundle1.putInt("paybackMethod", CommPaybackMethod);
                        bundle1.putInt("calculationMethod", 0);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case 1:
                        //按贷款金额计算
                        if (HAFCalculationMethod == HAF_CAPITAL) {
                            HAFMortgage = HAFMortgageEditText.getText().toString();
                        }
                        //按住房面积计算
                        if (HAFCalculationMethod == HAF_AREA) {
                            HAFMortgage = HAFPayEditText.getText().toString();
                        }
                        HAFRate = HAFRateTextView.getText().toString().substring(0, 5);

                        if (Double.valueOf(HAFMortgage) == 0) {
                            builder.setMessage("贷款金额不能为0");
                            builder.setTitle("提示");
                            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                            break;
                        }

                        Intent intent2 = new Intent();
                        intent2.setClass(MainActivity.this, ResultActivity.class);      //跳转到结果列表
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("mortgage", HAFMortgage);
                        bundle2.putString("time", HAFTime);
                        bundle2.putString("rate", HAFRate);
                        bundle2.putString("aheadTime", "0");
                        bundle2.putInt("firstYear", HAFFirstYear);
                        bundle2.putInt("firstMonth", HAFFirstMonth);
                        bundle2.putInt("paybackMethod", HAFPaybackMethod);
                        bundle2.putInt("calculationMethod", 1);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case 2:
                        //按贷款金额计算
                        if (CombCalculationMethod == COMB_CAPITAL) {
                            CombMortgageHAF = CombMortgageHAFEditText.getText().toString();
                            CombMortgageComm = CombMortgageCommEditText.getText().toString();

                            double sum = Double.valueOf(CombMortgageHAF) + Double.valueOf(CombMortgageComm);
                            CombPay = String.valueOf(sum);
                        }
                        //按住房面积计算
                        if (CombCalculationMethod == COMB_AREA) {
                            CombMortgageHAF = CombAreaHAFPayEditText.getText().toString();
                            CombMortgageComm = CombAreaCommPayEditText.getText().toString();

                            double sum = Double.valueOf(CombMortgageHAF) + Double.valueOf(CombMortgageComm);
                            CombPay = String.valueOf(sum);
                        }
                        CombHAFRate = CombHAFRateTextView.getText().toString().substring(0, 5);
                        CombCommRate = CombCommRateTextView.getText().toString().substring(0, 5);

                        if (Double.valueOf(CombMortgageHAF) == 0) {
                            builder.setMessage("公积金贷款金额不能为0");
                            builder.setTitle("提示");
                            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                            break;
                        }

                        Intent intent3 = new Intent();
                        intent3.setClass(MainActivity.this, ResultCombinationActivity.class);        //跳转到结果列表
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("mortgage", CombPay);                                         //贷款总额
                        bundle3.putString("HAFMortgage", CombMortgageHAF);                              //公积金贷款
                        bundle3.putString("commMortgage", CombMortgageComm);                            //商业贷款
                        bundle3.putString("time", CombTime);
                        bundle3.putString("HAFRate", CombHAFRate);                                      //公积金利率
                        bundle3.putString("commRate", CombCommRate);                                    //商业利率
                        bundle3.putString("aheadTime", "0");
                        bundle3.putInt("firstYear", CombFirstYear);
                        bundle3.putInt("firstMonth", CombFirstMonth);
                        bundle3.putInt("paybackMethod", CombPaybackMethod);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;
                }
            }
        });
    }

    //4.设置Spinner
    public void setSpinner() {
        //还款方式的下拉菜单
        final String[] paybackMethods = new String[2];
        paybackMethods[0] = "等额本息";
        paybackMethods[1] = "等额本金";

        ArrayAdapter<String> paybackMethodAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner1, paybackMethods) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = getLayoutInflater().from(MainActivity.this);
                View view = layoutInflater.inflate(R.layout.spinner_item1, null);
                TextView textView = view.findViewById(R.id.Spinner_Item_TextView);
                textView.setText(paybackMethods[position]);
                return view;
            }
        };
        CommPaybackMethodSpinner.setAdapter(paybackMethodAdapter);
        CommPaybackMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CommPaybackMethod = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CommPaybackMethodSpinner.setSelection(0, true);

        HAFPaybackMethodSpinner.setAdapter(paybackMethodAdapter);
        HAFPaybackMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HAFPaybackMethod = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        HAFPaybackMethodSpinner.setSelection(0, true);

        CombPaybackMethodSpinner.setAdapter(paybackMethodAdapter);
        CombPaybackMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CombPaybackMethod = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CombPaybackMethodSpinner.setSelection(0, true);

        //贷款年限的下拉菜单
        final String[] times = new String[22];
        for (int i = 0; i < 20; i++) {
            times[i] = (i + 1) + "年(" + (i + 1) * 12 + "期)";
        }
        times[20] = "25年(300期)";
        times[21] = "30年(360期)";

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner1, times) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = getLayoutInflater().from(MainActivity.this);
                View view = layoutInflater.inflate(R.layout.spinner_item1, null);
                TextView textView = view.findViewById(R.id.Spinner_Item_TextView);
                textView.setText(times[position]);
                return view;
            }
        };
        CommTimeSpinner.setAdapter(timeAdapter);
        CommTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 20) {
                    CommTime = String.valueOf(position + 1);
                } else if (position == 20) {
                    CommTime = "25";
                } else {
                    CommTime = "30";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CommTimeSpinner.setSelection(19, true);

        HAFTimeSpinner.setAdapter(timeAdapter);
        HAFTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 20) {
                    HAFTime = String.valueOf(position + 1);
                } else if (position == 20) {
                    HAFTime = "25";
                } else {
                    HAFTime = "30";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        HAFTimeSpinner.setSelection(19, true);

        CombTimeSpinner.setAdapter(timeAdapter);
        CombTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 20) {
                    CombTime = String.valueOf(position + 1);
                } else if (position == 20) {
                    CombTime = "25";
                } else {
                    CombTime = "30";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CombTimeSpinner.setSelection(19, true);

        //计算方式的下拉菜单
        final String[] calculationMethods = new String[2];
        calculationMethods[0] = "贷款金额";
        calculationMethods[1] = "住房面积";
        ArrayAdapter<String> calculationMethodAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner1, calculationMethods) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = getLayoutInflater().from(MainActivity.this);
                View view = layoutInflater.inflate(R.layout.spinner_item1, null);
                TextView textView = view.findViewById(R.id.Spinner_Item_TextView);
                textView.setText(calculationMethods[position]);
                return view;
            }
        };
        CommCalculationMethodSpinner.setAdapter(calculationMethodAdapter);
        CommCalculationMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        CommAreaDynamicLayout.setVisibility(View.GONE);
                        CommMortgageDynamicLayout.setVisibility(View.VISIBLE);
                        CommCalculationMethod = COMM_CAPITAL;
                        break;
                    case 1:
                        CommMortgageDynamicLayout.setVisibility(View.GONE);
                        CommAreaDynamicLayout.setVisibility(View.VISIBLE);
                        CommCalculationMethod = COMM_AREA;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CommCalculationMethodSpinner.setSelection(0, true);

        HAFCalculationMethodSpinner.setAdapter(calculationMethodAdapter);
        HAFCalculationMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        HAFAreaDynamicLayout.setVisibility(View.GONE);
                        HAFMortgageDynamicLayout.setVisibility(View.VISIBLE);
                        HAFCalculationMethod = HAF_CAPITAL;
                        break;
                    case 1:
                        HAFMortgageDynamicLayout.setVisibility(View.GONE);
                        HAFAreaDynamicLayout.setVisibility(View.VISIBLE);
                        HAFCalculationMethod = HAF_AREA;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        HAFCalculationMethodSpinner.setSelection(0, true);

        CombCalculationMethodSpinner.setAdapter(calculationMethodAdapter);
        CombCalculationMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        CombAreaDynamicLayout.setVisibility(View.GONE);
                        CombMortgageDynamicLayout.setVisibility(View.VISIBLE);
                        CombCalculationMethod = COMB_CAPITAL;
                        break;
                    case 1:
                        CombMortgageDynamicLayout.setVisibility(View.GONE);
                        CombAreaDynamicLayout.setVisibility(View.VISIBLE);
                        CombCalculationMethod = COMB_AREA;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CombCalculationMethodSpinner.setSelection(0, true);

        //首付比例的下拉菜单
        final String[] percents = new String[10];
        for (int i = 0; i < 9; i++) {
            percents[i] = (i + 1) + "成(" + (i + 1) * 10 + "%)";
        }
        ArrayAdapter<String> percentAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner1, percents) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = getLayoutInflater().from(MainActivity.this);
                View view = layoutInflater.inflate(R.layout.spinner_item1, null);
                TextView textView = view.findViewById(R.id.Spinner_Item_TextView);
                textView.setText(percents[position]);
                return view;
            }
        };
        CommFirstPaySpinner.setAdapter(percentAdapter);
        CommFirstPaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CommFirstPayPercent = String.valueOf(position + 1);
                setFistPay();                                       //3-3.计算首付和贷款金额
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CommFirstPaySpinner.setSelection(1, true);

        HAFFirstPaySpinner.setAdapter(percentAdapter);
        HAFFirstPaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HAFFirstPayPercent = String.valueOf(position + 1);
                setFistPay();                                       //3-3.计算首付和贷款金额
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        HAFFirstPaySpinner.setSelection(1, true);

        CombAreaFirstPaySpinner.setAdapter(percentAdapter);
        CombAreaFirstPaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CombFirstPayPercent = String.valueOf(position + 1);
                setFistPay();                                       //3-3.计算首付和贷款金额
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CombAreaFirstPaySpinner.setSelection(1, true);
    }

    //3-1.检查是否都填写了
    public void checkValidity(int calculationMethod) {
        switch (currentItem) {
            case 0: //商业贷款
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //按贷款金额计算
                if (calculationMethod == COMM_CAPITAL) {
                    String mortgageString = CommMortgageEditText.getText().toString();
                    if (mortgageString.length() == 0) {
                        CommMortgageEditText.setText("0");

                        builder.setMessage("请填写贷款金额");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

                //按住房面积计算
                else {
                    String area = CommAreaEditText.getText().toString();
                    String price = CommPriceEditText.getText().toString();
                    if (price.length() == 0) {
                        CommPriceEditText.setText("0.00");

                        builder.setMessage("请填写单价");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                    if (area.length() == 0) {
                        CommAreaEditText.setText("0.00");

                        builder.setMessage("请填写面积");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

                String rateString = CommRateEditText.getText().toString();
                String discountString = CommDiscountEditText.getText().toString();
                if (rateString.length() == 0) {
                    CommRateEditText.setText("4.9");

                    builder.setMessage("请填写商业贷款利率");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } else if (discountString.length() == 0) {
                    CommDiscountEditText.setText("1");

                    builder.setMessage("请填写商业贷款利率倍数");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

                setRateTextView();                    //3-2.计算利率
                break;
            }
            case 1: //公积金贷款
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //按贷款金额计算
                if (calculationMethod == HAF_CAPITAL) {
                    String mortgageString = HAFMortgageEditText.getText().toString();
                    if (mortgageString.length() == 0) {
                        HAFMortgageEditText.setText("0");

                        builder.setMessage("请填写贷款金额");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

                //按住房面积计算
                else {
                    String price = HAFPriceEditText.getText().toString();
                    String area = HAFAreaEditText.getText().toString();

                    if (price.length() == 0) {
                        HAFPriceEditText.setText("0.00");

                        builder.setMessage("请填写单价");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                    if (area.length() == 0) {
                        HAFAreaEditText.setText("0.00");

                        builder.setMessage("请填写面积");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

                String rateString = HAFRateEditText.getText().toString();
                String discountString = HAFDiscountEditText.getText().toString();
                if (rateString.length() == 0) {
                    HAFRateEditText.setText("3.25");

                    builder.setMessage("请填写公积金贷款利率");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                if (discountString.length() == 0) {
                    HAFDiscountEditText.setText("1");

                    builder.setMessage("请填写公积金贷款利率倍数");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

                setRateTextView();                    //3-2.计算利率
                break;
            }

            case 2://组合贷款
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //按贷款金额计算
                if (calculationMethod == COMB_CAPITAL) {
                    String mortgageHAFString = CombMortgageHAFEditText.getText().toString();
                    String mortgageCommString = CombMortgageCommEditText.getText().toString();
                    if (mortgageHAFString.length() == 0) {
                        CombMortgageHAFEditText.setText("0");

                        builder.setMessage("请填写公积金贷款金额");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                    if (mortgageCommString.length() == 0) {
                        builder.setMessage("请填写商业贷款金额");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

                //按面积计算
                if (calculationMethod == COMB_AREA) {
                    String price = CombAreaPriceEditText.getText().toString();
                    String area = CombAreaAreaEditText.getText().toString();
                    if (price.length() == 0) {
                        CombAreaPriceEditText.setText("0.00");

                        builder.setMessage("请填写单价");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                    if (area.length() == 0) {
                        CombAreaAreaEditText.setText("0.00");

                        builder.setMessage("请填写面积");
                        builder.setTitle("提示");
                        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

                String rateHAFString = CombHAFRateEditText.getText().toString();
                String discountHAFString = CombHAFDiscountEditText.getText().toString();
                String rateCommString = CombCommRateEditText.getText().toString();
                String discountCommString = CombCommDiscountEditText.getText().toString();
                if (rateHAFString.length() == 0) {
                    CombHAFRateEditText.setText("3.25");

                    builder.setMessage("请填写公积金贷款利率");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                if (discountHAFString.length() == 0) {
                    CombHAFDiscountEditText.setText("1");

                    builder.setMessage("请填写公积金贷款利率倍数");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                if (rateCommString.length() == 0) {
                    CombCommRateEditText.setText("4.90");

                    builder.setMessage("请填写商业贷款利率");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                if (discountCommString.length() == 0) {
                    CombCommDiscountEditText.setText("1");

                    builder.setMessage("请填写商业贷款利率倍数");
                    builder.setTitle("提示");
                    builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

                setRateTextView();                    //3-2.计算利率
                break;
            }
        }
    }

    //3-2.计算利率
    private void setRateTextView() {
        switch (currentItem) {
            case 0: {
                String rateString = CommRateEditText.getText().toString();
                String discountString = CommDiscountEditText.getText().toString();

                double originDouble = Double.valueOf(rateString);
                double discountDouble = Double.valueOf(discountString);
                double resultDouble = originDouble * discountDouble;

                DecimalFormat decimalFormat = new DecimalFormat("#.000");
                CommRate = decimalFormat.format(resultDouble) + "%";
                CommRateTextView.setText(CommRate);
                break;
            }

            case 1: {
                String rateString = HAFRateEditText.getText().toString();
                String discountString = HAFDiscountEditText.getText().toString();

                double originDouble = Double.valueOf(rateString);
                double discountDouble = Double.valueOf(discountString);
                double resultDouble = originDouble * discountDouble;

                DecimalFormat decimalFormat = new DecimalFormat("#.000");
                HAFRate = decimalFormat.format(resultDouble) + "%";
                HAFRateTextView.setText(HAFRate);
                break;
            }

            case 2: {
                String rateHAFString = CombHAFRateEditText.getText().toString();
                String discountHAFString = CombHAFDiscountEditText.getText().toString();
                String rateCommString = CombCommRateEditText.getText().toString();
                String discountCommString = CombCommDiscountEditText.getText().toString();

                double originHAFDouble = Double.valueOf(rateHAFString);
                double discountHAFDouble = Double.valueOf(discountHAFString);
                double resultHAFDouble = originHAFDouble * discountHAFDouble;

                double originCommDouble = Double.valueOf(rateCommString);
                double discountCommDouble = Double.valueOf(discountCommString);
                double resultCommDouble = originCommDouble * discountCommDouble;

                DecimalFormat decimalFormat1 = new DecimalFormat("#.000");
                CombHAFRate = decimalFormat1.format(resultHAFDouble) + "%";
                CombHAFRateTextView.setText(CombHAFRate);

                DecimalFormat decimalFormat2 = new DecimalFormat("#.000");
                CombCommRate = decimalFormat2.format(resultCommDouble) + "%";
                CombCommRateTextView.setText(CombCommRate);
            }
        }
    }

    //3-3.计算首付和贷款金额
    public void setFistPay() {
        switch (currentItem) {
            case 0: {
                String priceString = CommPriceEditText.getText().toString();
                String areaString = CommAreaEditText.getText().toString();
                String percentString = CommFirstPayPercent;

                double priceDouble = Double.valueOf(priceString);
                double areaDouble = Double.valueOf(areaString);
                double sum = priceDouble * areaDouble / 10000;
                double percentDouble = Double.valueOf(percentString) / 10;
                double firstPayDouble = priceDouble * areaDouble * percentDouble / 10000;
                double mortgageDouble = priceDouble * areaDouble * (1 - percentDouble) / 10000;

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                CommSum = decimalFormat.format(sum);                            //总价
                CommFirstPay = decimalFormat.format(firstPayDouble);            //首付的金额
                CommMortgage = decimalFormat.format(mortgageDouble);            //余下的金额

                CommAreaSumEditText.setText(CommSum);
                CommFirstPayEditText.setText(CommFirstPay);
                CommPayEditText.setText(CommMortgage);
                break;
            }

            case 1: {
                String priceString = HAFPriceEditText.getText().toString();
                String areaString = HAFAreaEditText.getText().toString();
                String percentString = HAFFirstPayPercent;

                double priceDouble = Double.valueOf(priceString);
                double areaDouble = Double.valueOf(areaString);
                double sum = priceDouble * areaDouble / 10000;
                double percentDouble = Double.valueOf(percentString) / 10;
                double firstPayDouble = priceDouble * areaDouble * percentDouble / 10000;
                double mortgageDouble = priceDouble * areaDouble * (1 - percentDouble) / 10000;

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                HAFSum = decimalFormat.format(sum);                            //总价
                HAFFirstPay = decimalFormat.format(firstPayDouble);            //首付的金额
                HAFMortgage = decimalFormat.format(mortgageDouble);            //余下的金额

                HAFAreaSumEditText.setText(HAFSum);
                HAFFirstPayEditText.setText(HAFFirstPay);
                HAFPayEditText.setText(HAFMortgage);
                break;
            }

            case 2: {
                String priceString = CombAreaPriceEditText.getText().toString();
                String areaString = CombAreaAreaEditText.getText().toString();
                String percentString = CombFirstPayPercent;
                CombMortgageHAF = CombAreaHAFPayEditText.getText().toString();

                double priceDouble = Double.valueOf(priceString);
                double areaDouble = Double.valueOf(areaString);
                double sumDouble = priceDouble * areaDouble / 10000;                               //总价
                double percentDouble = Double.valueOf(percentString) / 10;                          //首付比例
                double firstPayDouble = priceDouble * areaDouble * percentDouble / 10000;           //首付
                double mortgageDouble = priceDouble * areaDouble * (1 - percentDouble) / 10000;     //贷款总额
                double HAFDouble = Double.valueOf(CombMortgageHAF);                                 //公积金贷款金额

                double commDouble;
                if (mortgageDouble == 0) {
                    commDouble = 0;
                } else {
                    commDouble = mortgageDouble - HAFDouble;                                     //商业贷款金额
                }

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                CombSum = decimalFormat.format(sumDouble);                                          //总价
                CombFirstPay = decimalFormat.format(firstPayDouble);                                //首付的金额
                CombPay = decimalFormat.format(mortgageDouble);                                     //贷款总额
                CombMortgageHAF = decimalFormat.format(HAFDouble);                                  //公积金贷款金额
                CombMortgageComm = decimalFormat.format(commDouble);                                //商业贷款金额

                CombAreaSumEditText.setText(CombSum);
                CombAreaFirstPayEditText.setText(CombFirstPay);
                CombAreaPayEditText.setText(CombPay);
                CombAreaHAFPayEditText.setText(CombMortgageHAF);
                CombAreaCommPayEditText.setText(CombMortgageComm);
                break;
            }
        }
    }

    //2-1.ViewPager的监听器
    public class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //设置光标
            switch (position) {
                //TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
                case 0:
                    //从第二个到第一个
                    animation = new TranslateAnimation(offSet, 0, 0, 0);
                    break;
                case 1:
                    //从第一个到第二个
                    if (currentItem == 0) {
                        animation = new TranslateAnimation(0, offSet, 0, 0);
                    }
                    //从第三个到第二个
                    else if (currentItem == 2) {
                        animation = new TranslateAnimation(offSet * 2, offSet, 0, 0);
                    }
                    break;
                case 2:
                    //从第二个到第三个
                    animation = new TranslateAnimation(offSet, offSet * 2, 0, 0);
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
