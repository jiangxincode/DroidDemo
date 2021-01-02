package edu.jiangxin.droiddemo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.jiangxin.droiddemo.R;

public class FragmentTest1Activity extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private SoundFragment sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test1);
        manager = getSupportFragmentManager();

        transaction = manager.beginTransaction();
        sf = new SoundFragment();
        transaction.replace(R.id.container, sf);
        transaction.commit();  //提交事务
    }

    public void sound(View v) {
        transaction = manager.beginTransaction();
        SoundFragment sf = new SoundFragment();
        transaction.replace(R.id.container, sf);
        transaction.commit();
    }

    public void display(View v) {
        transaction = manager.beginTransaction();
        DisplayFragment df = new DisplayFragment();
        transaction.replace(R.id.container, df);
        transaction.commit();
    }

    public void storage(View v) {
        transaction = manager.beginTransaction();
        StorageFragment ssf = new StorageFragment();
        transaction.replace(R.id.container, ssf);
        transaction.commit();
    }

}
