package android.example.easya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout= (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout= (AppBarLayout) findViewById(R.id.appbarid);
        viewpager= (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());

        //Ajouter les fragments
        adapter.AddFragment(new identification_fragment(),"Identification");
        adapter.AddFragment(new inscription_fragment(),"Inscription");
        //Adapter
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }
}
