package com.katariya.bottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), BottomNavigationView.BottomMenuListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bottomNavigationView = findViewById(R.id.bottomView)

        //     bottomNavigationView.addChild(R.drawable.ic_chat_icon, null, null)
        //     bottomNavigationView.addChild(R.drawable.ic_contacts_icon, null, null)
        //     bottomNavigationView.addChild(R.drawable.ic_man_user_icon, null, null)

        bottomNavigationView.addChild(null, null, getString(R.string.title_home))
        bottomNavigationView.addChild(null, null, getString(R.string.title_home))
        bottomNavigationView.addChild(null, null, getString(R.string.title_notifications))


        //      bottomNavigationView.addChild(R.drawable.ic_chat_icon,R.drawable.ic_chat_icon, getString(R.string.title_chats))
        //      bottomNavigationView.addChild(R.drawable.ic_contacts_icon,R.drawable.ic_contacts_icon, getString(R.string.title_contacts))
        //      bottomNavigationView.addChild(R.drawable.ic_man_user_icon,R.drawable.ic_man_user_icon, getString(R.string.title_account))

        bottomNavigationView.initItemListener(this)

        bottomNavigationView.updateFont("fonts/roboto_medium.ttf")

        var count: Int = 0
        buttonUpdate.setOnClickListener {
            count++
            bottomNavigationView.setBadge(1, count)
        }
        buttonDelete.setOnClickListener {
            if (count >= 0)
                count--
            bottomNavigationView.setBadge(1, count)
        }
    }

    override fun bottomNavigationViewItemClickListener(position: Int) {
        if (position == 0) textTitle.text = getString(R.string.title_dashboard)
        if (position == 1) textTitle.text = getString(R.string.title_home)
        if (position == 2) textTitle.text = getString(R.string.title_notifications)
        //     Toast.makeText(applicationContext, "" + position, Toast.LENGTH_SHORT).show()
    }

}
