package at.htl.leonding.payyours

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object{
        val PAYMENT_KEY = "DEFAULT_PAYMENT"

    }
    var payment: Payment = Payment(0, 0, "");
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.menu_item_quit -> {
                finish()
                true
            }
            R.id.menu_item_settings -> {
                Log.d("MainActivity", "Setting menu called")
                val intent = Intent(this, SettingsActivity::class.java)
                getResult.launch(intent)
                //(Intent(this, SettingsActivity::class.java), SETTINGS_REQUEST)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        plaetzePlus.setOnClickListener { add(plaetzeEingabe, 1) }
        plaetzeMinus.setOnClickListener { add(plaetzeEingabe, -1) }
        spielerPlus.setOnClickListener { add(spielerEingabe, 1) }
        spielerMinus.setOnClickListener { add(spielerEingabe, -1) }

    }
    private fun updatePaymentTextView() {
        val payment = SettingsActivity.getStoredPayment(this)
        payment.players = spielerEingabe.text.toString().toInt()
        payment.courts = plaetzeEingabe.text.toString().toInt()
        val amountText = if (payment.amount > 0) {
            String.format(Locale.getDefault(),"%.2f", payment.amount)
        } else {
            "-"
        }

        betragErgebnis.text = amountText
        spielerMinus.isEnabled = payment.players > 1
        plaetzeMinus.isEnabled = payment.courts > 1
        Log.d(SettingsActivity.LOG_TAG, "Payment updated: $payment")
    }
    private fun add(textView: TextView, value: Int) {

        val newVal = textView.text.toString().toInt() + value
        textView.text = "$newVal"
        updatePaymentTextView()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        updatePaymentTextView()
    }
    // Receiver
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            payment = SettingsActivity.getStoredPayment(this)
            updatePaymentTextView()
        }



}