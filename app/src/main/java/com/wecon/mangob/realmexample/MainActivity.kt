package com.wecon.mangob.realmexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.wecon.mangob.realmexample.model.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var realm: Realm by Delegates.notNull()
    private var isFabOpen = false

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
        create()
        read()
        update(30)
        read()
        delete(30)
        read()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun setFabActions() {
        fab.setOnClickListener {
            animateFab()
        }
        fab_action_c.setOnClickListener(fabListener("action create"))
        fab_action_r.setOnClickListener(fabListener("action read"))
        fab_action_u.setOnClickListener(fabListener("action update"))
        fab_action_d.setOnClickListener(fabListener("action delete"))
    }

    private fun fabListener(msg: String) = View.OnClickListener {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
            fab_action_c.startAnimation(it)
            fab_action_r.startAnimation(it)
            fab_action_u.startAnimation(it)
            fab_action_d.startAnimation(it)
        }
        fab_action_c.isClickable = true
        fab_action_r.isClickable = true
        fab_action_u.isClickable = true
        fab_action_d.isClickable = true
    }

    private fun closeFab() {
        AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_backword)?.let {
            fab.startAnimation(it)
        }

        AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)?.let {
            fab_action_c.startAnimation(it)
            fab_action_r.startAnimation(it)
            fab_action_u.startAnimation(it)
            fab_action_d.startAnimation(it)
        }
        fab_action_c.isClickable = false
        fab_action_r.isClickable = false
        fab_action_u.isClickable = false
        fab_action_d.isClickable = false
    }


    private fun showStatus(txt: String) {
        container.addView(TextView(this).apply { text = txt })
    }

    private fun create() {
        realm.executeTransaction {
            realm.createObject(Person::class.java, 0).apply {
                name = "Young Person"
                age = 14
                showStatus("Create operation >> ${name} : ${age}")
            }
        }

    }

    private fun read() {
        realm.where(Person::class.java).findFirst()?.apply{
            showStatus("read operation >> ${name} : ${age}")
        }
    }

    private fun update(p0: Int) {
        realm.executeTransaction {
            realm.where(Person::class.java).findFirst().apply{
                name = "Old Person"
                age = p0
                showStatus("Update operation >> ${name} : ${age}")
            }
        }
    }

    private fun delete(p0: Int) {
        realm.executeTransaction {
            realm.where(Person::class.java).findFirst().apply {
                deleteFromRealm()
                showStatus("Delete operation >> ")
            }
        }
    }

    private fun showAll() {
        realm.where(Person::class.java)
    }

}
