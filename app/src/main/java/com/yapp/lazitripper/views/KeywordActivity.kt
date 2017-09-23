package com.yapp.lazitripper.views

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewStub
import android.widget.Toast
import com.google.gson.Gson
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import com.igalata.bubblepicker.rendering.BubblePicker

import com.yapp.lazitripper.R
import com.yapp.lazitripper.store.ConstantStore
import com.yapp.lazitripper.store.SharedPreferenceStore
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity
import kotlinx.android.synthetic.main.activity_keyword.*
import kotlinx.android.synthetic.main.chosen_place.*
import java.util.ArrayList

class KeywordActivity : BaseAppCompatActivity() {

    private val boldTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_BOLD) }
    private val mediumTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_MEDIUM) }
    private val regularTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_REGULAR) }

    val PREFS_FILENAME = "com.teamtreehouse.colorsarefun.prefs"
    val MY_KEYWORD = "com.teamtreehouse.colorsarefun.prefs"
    var prefs: SharedPreferences? = null

    private var stringArrayList: ArrayList<String> = ArrayList()

    companion object {
        private const val ROBOTO_BOLD = "NotoSansKR-Bold-Hestia.otf"
        private const val ROBOTO_MEDIUM = "NotoSansCJKkr-Thin.otf"
        private const val ROBOTO_REGULAR = "NotoSansKR-Regular-Hestia.otf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        setTitle("");


        val toolbar = findViewById(R.id.toolBar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar);


//        val fab = findViewById(R.id.fab) as FloatingActionButton
//        fab.setOnClickListener { view ->
//            savePreferences(ConstantStore.TAGS, stringArrayList)
//
//            val i = Intent(this@KeywordActivity, HomeActivity::class.java)
//            startActivity(i)
//            finish()
//            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in)
//        }


        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

        textView1.typeface = regularTypeface
        textView2.typeface = regularTypeface
        textView3.typeface = regularTypeface
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            subtitleTextView.letterSpacing = 0.06f
//            hintTextView.letterSpacing = 0.05f
//        }

        val titles = resources.getStringArray(R.array.countries)
        val colors = resources.obtainTypedArray(R.array.colors)
        val images = resources.obtainTypedArray(R.array.images)

        picker.adapter = object : BubblePickerAdapter {

            override val totalCount = titles.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = titles[position]
//                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
//                            colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                    color = Color.parseColor("#fff599");
                    typeface = boldTypeface
                    textColor = ContextCompat.getColor(this@KeywordActivity, android.R.color.black)

                    backgroundImage = ContextCompat.getDrawable(this@KeywordActivity, images.getResourceId(position, 0))
                }
            }


        }

        colors.recycle()
        images.recycle()

        picker.bubbleSize = 40
        picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                //toast("${item.title} selected")

                stringArrayList!!.add(item.title.toString())

            }

            override fun onBubbleDeselected(item: PickerItem) {

                //toast("${item.title} deselected")

                stringArrayList!!.remove(item.title.toString())
            }
        }

        btnFinish.setOnClickListener {
            view->
            savePreferences(ConstantStore.TAGS, stringArrayList)

            val i = Intent(this@KeywordActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in)
        }


    }

    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }

    fun savePreferences(key: String, value: ArrayList<String>) {

        val pref = this.getSharedPreferences(ConstantStore.STORE, 0)
        val editor = pref.edit()
        val gson = Gson()
        val json = gson.toJson(value)
        editor.putString(key, json)
        editor.commit()
    }
    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}
