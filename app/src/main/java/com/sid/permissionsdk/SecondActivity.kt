package com.sid.permissionsdk

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class SecondActivity : AppCompatActivity() {
    lateinit var firstFragment: Button
    lateinit var secondFragment:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        firstFragment =  findViewById(R.id.firstFragment);
        secondFragment = findViewById(R.id.secondFragment);

        firstFragment.setOnClickListener {
            loadFragment(TestFragment())
        }

       /* secondFragment.setOnClickListener {
            loadFragment(SecondFragment())

        }*/
    }
    private fun loadFragment(fragment: Fragment) {
// create a FragmentManager
        val fm: FragmentManager = supportFragmentManager
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit() // save the changes
    }
}

