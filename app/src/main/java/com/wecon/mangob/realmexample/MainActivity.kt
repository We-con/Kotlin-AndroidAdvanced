package com.wecon.mangob.realmexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.wecon.mangob.realmexample.model.Cat
import com.wecon.mangob.realmexample.model.Dog
import com.wecon.mangob.realmexample.model.Person
import io.realm.Realm
import io.realm.Sort
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

//        showStatus(complexReadWrite())
//        readDog()
//        readCat()
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

    private fun complexReadWrite(): String {
        var status = "\nPerforming complex Read/Write operation..."

        // Open the default realm. All threads must use its own reference to the realm.
        // Those can not be transferred across threads.
        val realm = Realm.getDefaultInstance()
        try {
            // Add ten persons in one transaction
            realm.executeTransaction {
                val fido = realm.createObject(Dog::class.java)
                fido.name = "fido"
                for (i in 1..9) {
                    val person = realm.createObject(Person::class.java, UUID.randomUUID().toString())
                    person.name = "Person no. $i"
                    person.age = i
                    person.dog = fido

                    // The field tempReference is annotated with @Ignore.
                    // This means setTempReference sets the Person tempReference
                    // field directly. The tempReference is NOT saved as part of
                    // the RealmObject:
                    person.tempReference = 42

                    for (j in 0..i - 1) {
                        val cat = realm.createObject(Cat::class.java)
                        cat.name = "Cat_$j"
                        person.cats.add(cat)
                    }
                }
            }

            // Implicit read transactions allow you to access your objects
            status += "\nNumber of persons: ${realm.where(Person::class.java).count()}"

            // Iterate over all objects
            for (person in realm.where(Person::class.java).findAll()) {
                val dogName: String = person?.dog?.name ?: "None"

                status += "\n${person.name}: ${person.age} : $dogName : ${person.cats.size}"

                // The field tempReference is annotated with @Ignore
                // Though we initially set its value to 42, it has
                // not been saved as part of the Person RealmObject:
                check(person.tempReference == 0)
            }

            // Sorting
            val sortedPersons = realm.where(Person::class.java).findAllSorted("age", Sort.DESCENDING)
            status += "\nSorting ${sortedPersons.last().name} == ${realm.where(Person::class.java).findAll().first().name}"

        } finally {
            realm.close()
        }
        return status
    }

    private fun readDog() {
        showStatus(">>Read Dog table")
        realm.where(Dog::class.java).findAll()?.let {
            when(it.size) {
                0 -> showStatus("There is no dog")
                else -> for(dog in it) showStatus("${dog.name}")
            }
        } ?: showStatus("Can't read dog")
    }

    private fun readCat() {
        showStatus(">>Read Cat table")
        realm.where(Cat::class.java).findAll()?.let {
            when(it.size) {
                0 -> showStatus("There is no cat")
                else -> for(cat in it) showStatus("${cat.name}")
            }
        } ?: showStatus("Can't read cat")
    }



}
