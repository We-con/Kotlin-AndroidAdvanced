package com.wecon.mangob.realmexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.wecon.mangob.realmexample.model.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.removeAllViews()

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
