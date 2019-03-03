package com.lang98.floatingactionmenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lang98.floatingactionmenu.FloatingActionMenu.FloatingActionModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onClick = View.OnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        }

        main_floating_action_menu
            ?.setMainButtonDrawableId(R.drawable.ic_add_black_24dp)
            ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_replay_black_16dp, R.color.purple, R.string.action_menu_1), onClick)
            ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_access_alarms_black_16dp, R.color.green, R.string.action_menu_2), onClick)
            ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_palette_black_16dp, R.color.red, R.string.action_menu_3), onClick)
            ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_library_add_black_16dp, R.color.blue, R.string.action_menu_4), onClick)
            ?.apply()
    }
}
