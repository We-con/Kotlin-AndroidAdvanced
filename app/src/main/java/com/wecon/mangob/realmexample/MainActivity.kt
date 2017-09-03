package com.wecon.mangob.realmexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.wecon.mangob.realmexample.model.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var realm: Realm by Delegates.notNull()
    private var isFabOpen = false
    private lateinit var fabs: ArrayList<FloatingActionButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.removeAllViews()

        setFabActions()

        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.deleteAll()
        }

        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...")
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun setFabActions() {
        fabs = ArrayList<FloatingActionButton>().apply {
            add(fab_action_c)
            add(fab_action_r)
            add(fab_action_u)
            add(fab_action_d)
        }
        fab.setOnClickListener(fabListener(-1))
        for((index, fab) in fabs.withIndex()) fab.setOnClickListener(fabListener(index))
    }

    private fun fabListener(type: Int) = View.OnClickListener {
        when(type) {
            0 -> create()
            1 -> read()
            2 -> update()
            3 -> delete()
            else -> null
        }
        animateFab()
    }

    private fun animateFab() {
        when(isFabOpen) {
            true -> closeFab()
            false -> openFab()
        }
        isFabOpen = !isFabOpen
    }

    private fun openFab() {
        AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_forward)?.let {
            fab.startAnimation(it)
        }

        AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)?.let {
            for(fab in fabs) fab.startAnimation(it)
        }
        for(fab in fabs) fab.isClickable = true
    }

    private fun closeFab() {
        AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_backword)?.let {
            fab.startAnimation(it)
        }

        AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)?.let {
            for(fab in fabs) fab.startAnimation(it)
        }
        for(fab in fabs) fab.isClickable = false
    }


    private fun showStatus(txt: String) {
        container.addView(TextView(this).apply { text = txt })
    }

    private fun create() {
        showStatus(">>Create operation")
        realm.executeTransaction {
            realm.createObject(Person::class.java, UUID.randomUUID().toString())?.apply {
                name = "Young Person"
                age = 14
                showStatus("${name} : ${age}")
            } ?: showStatus("Can't create person")
        }
    }

    private fun read() {
        showStatus(">>Read operation")
        realm.where(Person::class.java).findAll()?.let {
            when(it.size) {
                0 -> showStatus("There is no items")
                else -> for(person in it) showStatus("${person.name} : ${person.age}")
            }
        } ?: showStatus("Can't read person")
    }

    private fun update() {
        showStatus(">>Update operation")
        realm.executeTransaction {
            realm.where(Person::class.java).equalTo("name", "Young Person").findFirst()?.apply{
                name = "Old Person"
                age = 60
                showStatus("${name} : ${age}")
            } ?: showStatus("Can't update Young Person")
        }
    }

    private fun delete() {
        showStatus(">>Delete operation")
        realm.executeTransaction {
            realm.where(Person::class.java).equalTo("name", "Old Person").findFirst()?.apply {
                deleteFromRealm()
                showStatus("delete success")
            } ?: showStatus("Can't delete Old Person")
        }
    }

}
