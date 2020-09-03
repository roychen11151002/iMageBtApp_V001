package com.example.imagebtapp_v001b001

import android.app.Activity
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.STATE_OFF
import android.bluetooth.BluetoothAdapter.STATE_ON
import android.content.*
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Integer.parseInt

private const val LogMain = "testMain"
const val LogGbl = "testGlobal"

enum class CmdId(val value: Byte) {
    CMD_HEAD_FF(0xff.toByte()),
    CMD_HEAD_55(0x55.toByte()),
    CMD_DEV_HOST(0x80.toByte()),
    CMD_DEV_SRC(0x30.toByte()),
    CMD_DEV_AG(0x00.toByte()),
    CMD_DEV_AG_ALL(0x38.toByte()),
    CMD_DEV_HFP_ALL(0x38.toByte()),
    SET_HFP_PAIR_REQ(0x02.toByte()),
    SET_HFP_PAIR_RSP(0x03.toByte()),
    SET_AG_VOL_REQ(0x06.toByte()),
    SET_AG_VOL_RSP(0x07.toByte()),
    SET_HFP_VOL_REQ(0x08.toByte()),
    SET_HFP_VOL_RSP(0x09.toByte()),
    SET_HFP_STA_REQ(0x0a.toByte()),
    SET_HFP_STA_RSP(0x0b.toByte()),
    SET_HFP_EXT_STA_REQ(0x0c.toByte()),
    SET_HFP_EXT_STA_RSP(0x0d.toByte()),
    SET_HFP_PSKEY_REQ(0x10.toByte()),
    SET_HFP_PSKEY_RSP(0x11.toByte()),
    SET_AG_PSKEY_REQ(0x12.toByte()),
    SET_AG_PSKEY_RSP(0x13.toByte()),
    SET_HFP_LOCAL_NAME_REQ(0x14.toByte()),
    SET_HFP_LOCAL_NAME_RSP(0x15.toByte()),
    SET_AG_LOCAL_NAME_REQ(0x16.toByte()),
    SET_AG_LOCAL_NAME_RSP(0x17.toByte()),
    SET_HFP_FEATURE_REQ(0x1c.toByte()),
    SET_HFP_FEATURE_RSP(0x1d.toByte()),
    SET_AG_FEATURE_REQ(0x1e.toByte()),
    SET_AG_FEATURE_RSP(0x1f.toByte()),
    SET_HFP_DIAL_REQ(0x20.toByte()),
    SET_HFP_DIAL_RSP(0x21.toByte()),
    SET_DISCOVERY_REQ(0x22.toByte()),
    SET_DISCOVERY_RSP(0x23.toByte()),
    SET_HFP_TEST_REQ(0x34.toByte()),
    SET_HFP_TEST_RSP(0x35.toByte()),
    SET_AG_TEST_REQ(0x36.toByte()),
    SET_AG_TEST_RSP(0x37.toByte()),
    SET_HFP_POWER_REQ(0x38.toByte()),
    SET_HFP_POWER_RSP(0x39.toByte()),
    SET_AG_DFU_REQ(0x3c.toByte()),
    SET_AG_DFU_RSP(0x3d.toByte()),
    SET_HFP_DFU_REQ(0x3e.toByte()),
    SET_HFP_DFU_RSP(0x3f.toByte()),
    SET_INT_SERVICE_REQ(0xe0.toByte()),
    SET_INT_SERVICE_RSP(0xe1.toByte()),
    SET_INT_CON_REQ(0xe2.toByte()),
    SET_INT_CON_RSP(0xe3.toByte()),
    SET_INT_DISCOVERY_REQ(0xe6.toByte()),
    SET_INT_DISCOVERY_RSP(0xe7.toByte()),
    SET_INT_PAIR_REQ(0xe8.toByte()),
    SET_INT_PAIR_RSP(0xe9.toByte()),

    GET_HFP_PAIR_REQ(0x42.toByte()),
    GET_HFP_PAIR_RSP(0x43.toByte()),
    GET_AG_VOL_REQ(0x46.toByte()),
    GET_AG_VOL_RSP(0x47.toByte()),
    GET_HFP_VOL_REQ(0x48.toByte()),
    GET_HFP_VOL_RSP(0x49.toByte()),
    GET_HFP_STA_REQ(0x4a.toByte()),
    GET_HFP_STA_RSP(0x4b.toByte()),
    GET_HFP_EXT_STA_REQ(0x4c.toByte()),
    GET_HFP_EXT_STA_RSP(0x4d.toByte()),
    GET_SRC_DEV_NO_REQ(0x4e.toByte()),
    GET_SRC_DEV_NO_RSP(0x4f.toByte()),
    GET_HFP_PSKEY_REQ(0x50.toByte()),
    GET_HFP_PSKEY_RSP(0x51.toByte()),
    GET_AG_PSKEY_REQ(0x52.toByte()),
    GET_AG_PSKEY_RSP(0x53.toByte()),
    GET_HFP_LOCAL_NAME_REQ(0x54.toByte()),
    GET_HFP_LOCAL_NAME_RSP(0x55.toByte()),
    GET_AG_LOCAL_NAME_REQ(0x56.toByte()),
    GET_AG_LOCAL_NAME_RSP(0x57.toByte()),
    GET_HFP_VRESION_REQ(0x58.toByte()),
    GET_HFP_VRESION_RSP(0x59.toByte()),
    GET_AG_VRESION_REQ(0x5a.toByte()),
    GET_AG_VRESION_RSP(0x5b.toByte()),
    GET_HFP_FEATURE_REQ(0x5c.toByte()),
    GET_HFP_FEATURE_RSP(0x5d.toByte()),
    GET_AG_FEATURE_REQ(0x5e.toByte()),
    GET_AG_FEATURE_RSP(0x5f.toByte()),
    GET_HFP_DIAL_REQ(0x60.toByte()),
    GET_HFP_DIAL_RSP(0x61.toByte()),
    GET_HFP_RSSI_REQ(0x70.toByte()),
    GET_HFP_RSSI_RSP(0x71.toByte()),
    GET_HFP_BDA_REQ(0x78.toByte()),
    GET_HFP_BDA_RSP(0x79.toByte()),
    GET_AG_BDA_REQ(0x7a.toByte()),
    GET_AG_BDA_RSP(0x7b.toByte())
}
class BtDevMsg(var btDevNo: Int = 0, var btGroup: Int = 0) {
    var btCmd = ByteArray(256 + 7)
}

class BtDevUnit {
    companion object {
        // lateinit var resolver: ContentResolver
        var sppStateCon: Byte = 1
        var PairState = 0
        val MaxBtDev = 2
        var previousItem = 256
        var isReconnect = true
        var staUpdateInterval = 600
        val BtPermissionReqCode = 1
        val BtActionReqCode = 66
        var BtList = ArrayList<String>()
        var maxTalkNo = 0
        var maxAgNo = 0
        var deviceNo = 6
        val featureMaxTalkNo = 4
        val featuerMaxAgNo = 0x10
        val featureBdaddrFilter = "C4:FF:BC:00:00:00"
        val featuerM6Src = 0xa222
        val ledPwrM6Src = 0x03ff
        val ledMfbM6Src = 0x03ff
        val ledBcbM6Src =0x03ff
        val ledRevM6Src = 0x03ff
        val featuerM6Ag = 0xa220
        val ledPwrM6Ag = 0x03ff
        val ledMfbM6Ag = 0x03ff
        val ledBcbM6Ag =0x03ff
        val ledRevM6Ag = 0x03ff
        val featuerDg = 0xa221
        val ledPwrDg = 0x03ff
        val ledMfbDg = 0x03ff
        val ledBcbDg =0x03ff
        val ledRevDg = 0x03ff
        val featuerVc = 0xa220
        val ledPwrVc = 0x03ff
        val ledMfbVc = 0x03ff
        val ledBcbVc =0x03ff
        val ledRevVc = 0x03ff
        val featuerA6 = 0x8002
        val ledPwrA6 = 0x03ff
        val ledMfbA6 = 0x03ff
        val ledBcbA6 =0x03ff
        val ledRevA6 = 0x03ff
        val featuerA7 = 0x8003
        val ledPwrA7 = 0x03ff
        val ledMfbA7 = 0x03ff
        val ledBcbA7 =0x03ff
        val ledRevA7 = 0x03ff
    }
    var imgIconUri = Uri.parse("")
    var imgIconFlash = true
    var bdaddr = "00:00:00:00:00:00"
    var bdaddrPair = "00:00:00:00:00:00"
    var bdaddrFilterHfp = "00:00:00:00:00:00"
    var bdaddrFilterAg = "00:00:00:00:00:00"
    var ledLightHfp = arrayOf(0, 0, 0, 0)
    var ledLightAg = arrayOf(0, 0, 0, 0)
    var verFirmwareHfp = ""
    var verFirmwareAg = ""
    var nameAlias = "alias name"
    var nameLocalHfp = ""
    var nameLocalAg = ""
    var featureHfp = 0
    var featureAg = 0
    var featureMode = 0x01000000
    var stateCon = 0
    var stateDial: Int = 0
    var stateExtra: Int = 0
    var rssi: Int = 0
    var volSpkrHfp: Int = 15
    var volMicHfp: Int = 15
    var muteSpkr: Boolean = false
    var muteMic: Boolean = false
    var batInd: Boolean = false
    var devDfu: Boolean = false
    var volSpkrAg: Int = 15
    var volMicAg: Int = 15
    var batLevel: Int = 0
    var modeSrcWireHfpMicVol = 15
    var modeSrcWireHfpSpkrVol = 15
    var modeSrcWireAvSpkrVol = 15
    var modeSrcUsbHfpMicVol = 15
    var modeSrcUsbHfpSpkrVol = 15
    var modeSrcUsbAvSpkrVol = 15
    var modeSrcBtHfpMicVol = 15
    var modeSrcBtHfpSpkrVol = 15
    var modeSrcBtAvSpkrVol = 15
    var modeSrcVcsHfpMicVol = 15
    var modeSrcVcsHfpSpkrVol = 15
    var modeSrcVcsAvSpkrVol = 15
    var modeSrcSpkrDecade = 0
    var modeAgWireHfpMicVol = 15
    var modeAgWireHfpSpkrVol = 15
    var modeAgWireAvSpkrVol = 15
    var modeAgUsbHfpMicVol = 15
    var modeAgUsbHfpSpkrVol = 15
    var modeAgUsbAvSpkrVol = 15
    var modeAgBtHfpMicVol = 15
    var modeAgBtHfpSpkrVol = 15
    var modeAgBtAvSpkrVol = 15
    var modeAgVcsHfpMicVol = 15
    var modeAgVcsHfpSpkrVol = 15
    var modeAgVcsAvSpkrVol = 15
}

interface DevUnitMsg {
    fun getpreferData(): SharedPreferences
    fun getBtDevUnitList(): ArrayList<BtDevUnit>
    fun sendBtServiceMsg(msg: BtDevMsg)
    fun getDevType(devNo: Int): String
}

val ViewPagerArray = arrayOf(FragmentConState(),
                                              FragmentAudioParaSetA6(),
                                              FragmentAudioParaSetA7(),
                                              FragmentPairSet(),
                                              FragmentFeatureSet(),
                                              FragmentVolSet(),
                                              FragmentRfTest())

class MainActivity : AppCompatActivity(), DevUnitMsg {
    private var BtDevUnitList = ArrayList<BtDevUnit>()
    private val adapterPager = ViewPagerAdapter(supportFragmentManager)
    private val MainBackGroundImage = 88
    private lateinit var preferData: SharedPreferences
    private var iMageBtServiceBind = false
    private lateinit var iMageBtServiceMsg: Messenger
    private val clientMsgHandler = Messenger(Handler(Handler.Callback {
        when(it.what) {
            0 -> {
                Logger.d(LogMain, "iMage service message send success")
            }
            else -> {
                val btDevMsg = it.obj as BtDevMsg?
                btDevMsg?.let{
                    when(it.btDevNo) {
                        0 -> {

                        }
                        1 -> {

                        }
                        else -> {

                        }
                    }
                    iMageCmdParse(it)
                }
            }
        }
        true
    }))
    private val iMageBtServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val msg = Message.obtain(null, 0, BtDevMsg(0, 0))

            Logger.d(LogMain, "onServiceConnected")
            iMageBtServiceBind = true
            iMageBtServiceMsg = Messenger(service)                  // send client messenger handler to iMageBtService
            msg.replyTo = clientMsgHandler
            iMageBtServiceMsg.send(msg)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Logger.d(LogMain, "onServiceDisconnected")
            iMageBtServiceBind = false
        }
    }

    private val viewM6UpdateHandler = Messenger(Handler(Handler.Callback {
        when(ViewPagerArray[it.what]) {
            is FragmentConState -> {
                (ViewPagerArray[it.what] as FragmentConState).updateData()
                Logger.d(LogMain, "FragmentConState fragment update")
            }
            is FragmentPairSet -> {
                (ViewPagerArray[it.what] as FragmentPairSet).updateData()
                Logger.d(LogMain, "FragmentPairSet fragment update")
            }
            is FragmentFeatureSet -> {
                (ViewPagerArray[it.what] as FragmentFeatureSet).updateData()
                Logger.d(LogMain, "FragmentFeatureSet fragment update")
            }
            is FragmentVolSet -> {
                (ViewPagerArray[it.what] as FragmentVolSet).updateData()
                Logger.d(LogMain, "FragmentVolSet fragment update")
            }
            is FragmentAudioParaSetA6 -> {
                (ViewPagerArray[it.what] as FragmentAudioParaSetA6).updateData()
                Logger.d(LogMain, "FragmentAudioParaSetA6 fragment update")
            }
            is FragmentAudioParaSetA7 -> {
                (ViewPagerArray[it.what] as FragmentAudioParaSetA7).updateData()
                Logger.d(LogMain, "FragmentAudioParaSetA7 fragment update")
            }
            is FragmentRfTest -> {
                (ViewPagerArray[it.what] as FragmentRfTest).updateData()
                Logger.d(LogMain, "FragmentRfTest fragment update")
            }
        }
        true
    }))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val intentFilter = IntentFilter()

        // requestWindowFeature(Window.FEATURE_NO_TITLE)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        setContentView(R.layout.activity_main)
        Logger.d(LogMain, "onCreate")
        supportActionBar?.hide()                                                        // hide action bar
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT                 // set screen
        // requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // intentFilter.addAction("iMageBroadcastMain")                            // register broadcast receiver
        // registerReceiver(iMageBtBroadcast(), intentFilter)
        btnStaUpdate.setOnClickListener {
            setUpdate()
            stateUpdate()
        }
        btnStaUpdate.setOnLongClickListener {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

            intent.setType("image/*")
            startActivityForResult(intent, MainBackGroundImage)
            true
        }
        btnCon.setOnClickListener {
            var sendMsg = BtDevMsg(0, 1)
            var strList = preferData.getString("bdaddr${0}", "00:00:00:00:00:00")!!.split(':')

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
            sendMsg.btCmd[5] = 0x07
            sendMsg.btCmd[6] = 0x01
            sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
            sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
            sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
            sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
            sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
            sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
            Logger.d(LogGbl, "bdaddr ${bdaddrTranslate(sendMsg, 7)}")
            sendBtServiceMsg(sendMsg)
        }
        btnCon.setOnLongClickListener {
/*
            var sendMsg = BtDevMsg(1, 1)
            var strList: List<String> = preferData.getString("bdaddr${1}", "00:00:00:00:00:00")!!.split(':')

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
            sendMsg.btCmd[5] = 0x07
            sendMsg.btCmd[6] = 0x01
            sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
            sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
            sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
            sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
            sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
            sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
            Logger.d(LogGbl, "bdaddr ${bdaddrTranslate(sendMsg, 7)}")
            sendBtServiceMsg(sendMsg)
            true
 */
            val sendMsg = arrayOf(BtDevMsg(0, 0), BtDevMsg(0, 0))

            AlertDialog.Builder(this).setTitle("Set Device power off").setPositiveButton("OK") { dialog, which ->
                sendMsg[0].btCmd[3] = CmdId.CMD_DEV_AG_ALL.value
                sendMsg[1].btCmd[3] = CmdId.CMD_DEV_SRC.value
                for (i in 0 until sendMsg.size) {
                    sendMsg[i].btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg[i].btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg[i].btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg[i].btCmd[4] = CmdId.SET_HFP_POWER_REQ.value
                    sendMsg[i].btCmd[5] = 0x01.toByte()
                    sendMsg[i].btCmd[6] = 0x00.toByte()
                    Handler().postDelayed({ sendBtServiceMsg(sendMsg[i]) }, i.toLong() * 1000)
                }
            }.setNegativeButton("CANCEL") {dialog, which ->
            }.show()
            true
        }
        editTextStaUpTime.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val preferDataEdit = preferData.edit()

                imm.hideSoftInputFromWindow(editTextStaUpTime.getWindowToken(), 0)
                editTextStaUpTime.clearFocus()
                BtDevUnit.staUpdateInterval = parseInt(editTextStaUpTime.text.toString())
                preferDataEdit.putInt("staUpdateInterval", BtDevUnit.staUpdateInterval)
                preferDataEdit.apply()
                true
            }
            else {
                false
            }
        }
        seekMainAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val preferDataEdit = preferData.edit()

                preferDataEdit.putInt("MainIconAlpha", seekMainAlpha.progress)
                preferDataEdit.apply()
                imgMainBackGround.alpha = seekMainAlpha.progress.toFloat() / seekMainAlpha.max
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        preferData = getSharedPreferences("iMageBdaList", Context.MODE_PRIVATE)     // create prefer data
        BtDevUnit.staUpdateInterval = preferData.getInt("staUpdateInterval", 600)
        editTextStaUpTime.setText(BtDevUnit.staUpdateInterval.toString())
        stateUpdateAuto(BtDevUnit.staUpdateInterval.toLong() * 1000)
        Glide.with(applicationContext).load(Uri.parse(preferData.getString("imgIconUri", ""))).error(R.drawable.img_background).into(imgMainBackGround)
        imgMainBackGround.alpha = preferData.getInt("MainIconAlpha", 3).toFloat() / seekMainAlpha.max
        seekMainAlpha.progress = preferData.getInt("MainIconAlpha", 3)
        initBt()
        Logger.d(LogMain, "state update interval ${BtDevUnit.staUpdateInterval}")
    }

    override fun onStart() {
        super.onStart()
        Logger.d(LogMain, "main activity onStart")
    }

    override fun onStop() {
        super.onStop()
/*
        for(i in 0 until MaxBtDev) {
            var sendMsg = BtDevMsg(i, 1)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
            sendMsg.btCmd[5] = 0x07
            sendMsg.btCmd[6] = 0x00
            sendMsg.btCmd[7] = 0x00
            sendMsg.btCmd[8] = 0x00
            sendMsg.btCmd[9] = 0x00
            sendMsg.btCmd[10] = 0x00
            sendMsg.btCmd[11] = 0x00
            sendMsg.btCmd[12] = 0x00
            sendBtServiceMsg(sendMsg)
        }
*/
        Logger.d(LogMain, "main activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(LogMain, "main activity onDestroy")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Logger.d(LogMain, "request permission result")
        if(requestCode == BtDevUnit.BtPermissionReqCode) {
            if(grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                initBt()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d(LogMain, " activity result:$resultCode requestCode:$requestCode")
        when(requestCode) {
            BtDevUnit.BtActionReqCode -> {
                if(resultCode == Activity.RESULT_OK) {
                    Logger.d(LogMain, "bluetooth enabled")
                    initBt()
                } else {
                    Logger.d(LogMain, "bluetooth disable")
                    finish()
                }
            }
            MainBackGroundImage ->  {
                var preferDataEdit = preferData.edit()

                if(resultCode == Activity.RESULT_OK) {
                    preferDataEdit.putString("imgIconUri", data?.data.toString())
                    preferDataEdit.apply()
                    Glide.with(applicationContext).load(data?.data).error(R.drawable.android_image_1).into(imgMainBackGround)
                    imgMainBackGround.alpha = seekMainAlpha.progress.toFloat() / seekMainAlpha.max
                    Logger.d(LogGbl, "result code OK; image icon URI: ${data?.data?.toString()}")
                }
                else
                    Logger.d(LogGbl, "result code $resultCode")
            }
            else -> {
                Logger.d(LogGbl, "other request code")
            }
        }
    }
/*
    inner class iMageBtBroadcast : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Logger.d(LogMain, "iMageBtBroadcast receive")
            when(intent!!.action) {
                "iMageBroadcastMain" -> {
                }
                else -> {
                }
            }
        }
    }
*/
    fun initBt() {
        Logger.d(LogMain, "initBt")
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) && (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Logger.d(LogMain, "bluetooth permission request")
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), BtDevUnit.BtPermissionReqCode)
        } else if (BluetoothAdapter.getDefaultAdapter().isEnabled) {
            Logger.d(LogMain, "bluetooth enabled")
            // txvConSta0.text = "Enable"
            // txvConSta1.text = "Enable"
            initHfpDevice(BtDevUnit.deviceNo)
            viewPagerM6.adapter = adapterPager
            if (!iMageBtServiceBind) {
                // startService(Intent(this, iMageBtService::class.java))
                bindService(Intent(this, iMageBtService::class.java), iMageBtServiceConnection, Context.BIND_AUTO_CREATE)
            }
            else {
/*
                var strList: List<String>

                for (i in 0 until MaxBtDev) {
                    var sendMsg = BtDevMsg(0, 1)

                    strList = preferData.getString("bdaddr${i}", "00:00:00:00:00:00")!!.split(':')
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                    sendMsg.btCmd[5] = 0x07
                    sendMsg.btCmd[6] = 0x01
                    sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
                    sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
                    sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
                    sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
                    sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
                    sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
                    Logger.d(LogMain, "bdaddr ${bdaddrTranslate(sendMsg, 7)}")
                    sendMsg.btDevNo = i
                    sendBtServiceMsg(sendMsg)
                }
 */
            }
        } else {
            Logger.d(LogMain, "bluetooth action request")
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BtDevUnit.BtActionReqCode)
        }
    }

    fun initHfpDevice(dev: Int) {
        BtDevUnitList.removeAll(BtDevUnitList)
        BtDevUnitList.add(BtDevUnit())
        BtDevUnitList[0].nameAlias = preferData!!.getString("nameAlias0", "alias name src")
        BtDevUnitList[0].imgIconUri = Uri.parse(preferData.getString("imgIconUri0", ""))
        Logger.d(LogMain, "imgIconUri0: ${BtDevUnitList[0].imgIconUri}")
        for (i in 0 until dev) {
            BtDevUnitList.add(BtDevUnit())
            BtDevUnitList[i + 1].nameAlias = preferData!!.getString("nameAlias${i + 1}", "alias name hfp$i")
            BtDevUnitList[i + 1].imgIconUri = Uri.parse(preferData.getString("imgIconUri${i + 1}", ""))
            BtDevUnitList[i + 1].nameLocalHfp = BtDevUnitList[i + 1].nameAlias
        }
    }

    override fun sendBtServiceMsg(msg: BtDevMsg) {
        val sendMsg = Message.obtain(null, 1, msg)

        if(iMageBtServiceBind)
            iMageBtServiceMsg.send(sendMsg)
/*
        when(msg.btGroup) {
            0 -> sendBroadcast(Intent("iMageBtBroadcastService").putExtra("btServiceMsg", msg))
            1 -> sendBroadcast(Intent("iMageBtBroadcastService").putExtra("btServiceMsg", msg))
            else -> Logger.d(LogMain, "send broadcast other message")
        }
 */
    }

    override fun getDevType(devNo: Int): String {
        return if (BtDevUnitList[devNo].nameLocalHfp.substring(0, 12).compareTo("iMage M6_SRC") == 0) {
            "M6_SRC"
        }
        else if (BtDevUnitList[devNo].nameLocalHfp.substring(0, 11).compareTo("iMage DG_BT") == 0) {
            "DG_BT"
        }
        else if (BtDevUnitList[devNo].nameLocalHfp.substring(0, 11).compareTo("iMage VC_BT") == 0) {
            "VC_BT"
        }
        else if (BtDevUnitList[devNo].nameLocalHfp.substring(0, 11).compareTo("iMage A6_BT") == 0) {
            "A6"
        }
        else if (BtDevUnitList[devNo].nameLocalHfp.substring(0, 11).compareTo("iMage A7_BT") == 0) {
            "A7"
        }
        else {
            "UNKNOW"
        }
    }

    override fun getBtDevUnitList(): ArrayList<BtDevUnit> = BtDevUnitList
    override fun getpreferData(): SharedPreferences = preferData

    fun viewM6UpdateNest() {
        Handler().postDelayed({
            if(BtDevUnit.previousItem != viewPagerM6.currentItem) {
                viewM6Update()
                BtDevUnit.previousItem = viewPagerM6.currentItem
            }
            viewM6UpdateNest()
        }, 1000)
    }

    fun viewM6Update() = viewM6UpdateHandler.send(Message.obtain(null, viewPagerM6.currentItem))

    fun stateUpdateAuto(time: Long) {
        Handler().postDelayed({
            stateUpdate()
            if(BtDevUnit.staUpdateInterval >= 3)
                stateUpdateAuto(BtDevUnit.staUpdateInterval.toLong() * 1000)
        }, time)
    }

    fun setUpdate() {
        val srcDevId = arrayOf(CmdId.CMD_DEV_SRC.value, CmdId.CMD_DEV_AG_ALL.value)
        val pskey = arrayOf(9, 16, 17, 18, 26)
        val cmdId = arrayOf(
            CmdId.GET_HFP_LOCAL_NAME_REQ.value,
            CmdId.GET_AG_LOCAL_NAME_REQ.value,
            CmdId.GET_HFP_VRESION_REQ.value,
            CmdId.GET_AG_VRESION_REQ.value,
            CmdId.GET_HFP_FEATURE_REQ.value,
            CmdId.GET_AG_FEATURE_REQ.value,
            CmdId.GET_HFP_BDA_REQ.value,
            CmdId.GET_AG_BDA_REQ.value,
            CmdId.GET_HFP_PAIR_REQ.value)

        for(j in 0 until srcDevId.size) {
            var srcDevId = srcDevId[j]
            for (i in 0 until cmdId.size) {
                var sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = srcDevId
                sendMsg.btCmd[4] = cmdId[i]
                sendMsg.btCmd[5] = 0x00
                sendBtServiceMsg(sendMsg)
            }
        }
        for(j in 0 until srcDevId.size) {
            var srcDevId = srcDevId[j]
            for(i in 0 until pskey.size) {
                var sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = srcDevId
                sendMsg.btCmd[4] = CmdId.GET_HFP_PSKEY_REQ.value
                sendMsg.btCmd[5] = 0x02
                sendMsg.btCmd[6] = 0x00
                sendMsg.btCmd[7] = pskey[i].toByte()
                sendBtServiceMsg(sendMsg)
            }
        }
    }

    fun stateUpdate() {
        val srcDevId = arrayOf(CmdId.CMD_DEV_SRC.value, CmdId.CMD_DEV_AG_ALL.value)
        val cmdId = arrayOf(
            CmdId.GET_HFP_VOL_REQ.value,
            CmdId.GET_HFP_STA_REQ.value,
            CmdId.GET_HFP_EXT_STA_REQ.value,
            CmdId.GET_HFP_PAIR_REQ.value,
            CmdId.GET_HFP_RSSI_REQ.value)

        for(j in 0 until srcDevId.size) {
            var srcDevId = srcDevId[j]
            for (i in 0 until cmdId.size) {
                var sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = srcDevId
                sendMsg.btCmd[4] = cmdId[i]
                sendMsg.btCmd[5] = 0x00
                sendBtServiceMsg(sendMsg)
            }
        }
    }

    fun getBtDevId(id: Byte) =
        when(id) {
            0x30.toByte() -> 0
            0x00.toByte() -> 1
            0x08.toByte() -> 2
            0x10.toByte() -> 3
            0x18.toByte() -> 4
            0x20.toByte() -> 5
            0x28.toByte() -> 6
            CmdId.CMD_DEV_HOST.value -> 0x80
            else -> 0xff
        }

    fun bdaddrTranslate(msg: BtDevMsg, offset: Int) = String.format("%02X:%02X:%02X:%02X:%02X:%02X", msg.btCmd[offset + 4], msg.btCmd[offset + 5], msg.btCmd[offset + 3], msg.btCmd[offset], msg.btCmd[offset + 1], msg.btCmd[offset + 2])

    fun iMageCmdParse(msg: BtDevMsg) {
/*
        val len = msg.btCmd[5] + 6
        var cmdStr = ""
        for(i: Int in 0..len)
            cmdStr += String.format("%02X ", msg.btCmd[i])
        Logger.d(LogMain, "${cmdStr}")
*/
        // Logger.d(LogMain, "${String.format("command src:%02X id:%02X", msg.btCmd[2], msg.btCmd[4])}")
        var id = getBtDevId(msg.btCmd[2])

        if ((id < BtDevUnitList.size) || (id == 0x80)) {
            when (msg.btCmd[4]) {
                CmdId.SET_AG_VOL_RSP.value -> Logger.d(LogMain, "${String.format("src:%02X AG volume set", msg.btCmd[2])}")
                CmdId.SET_HFP_VOL_RSP.value -> Logger.d(LogMain, "${String.format("src:%02X HFP volume set", msg.btCmd[2])}")
                CmdId.SET_HFP_PAIR_RSP.value -> Logger.d(LogMain, "${String.format("src:%02X set hfp pair bda", msg.btCmd[2])}")
                CmdId.SET_HFP_PSKEY_RSP.value -> Logger.d(LogMain, "${String.format("src:%02X set hfp pskey", msg.btCmd[2])}")
                CmdId.SET_AG_PSKEY_RSP.value -> Logger.d(LogMain, "${String.format("src:%02X set ag pskey", msg.btCmd[2])}")
                CmdId.SET_HFP_FEATURE_RSP.value -> Logger.d(LogMain, "${String.format("src:%02X set hfp feature", msg.btCmd[2])}")
                CmdId.GET_SRC_DEV_NO_REQ.value -> Logger.d(LogMain, "${String.format("src:%02X get device number", msg.btCmd[2])}")
                CmdId.GET_HFP_STA_RSP.value -> {
                    BtDevUnitList[id].stateCon = msg.btCmd[6].toInt().and(0xff).shl(24) + msg.btCmd[7].toInt().and(0xff).shl(16) + msg.btCmd[8].toInt().and(0xff).shl(8) + msg.btCmd[9].toInt().and(0xff)
                    BtDevUnitList[id].volSpkrHfp = msg.btCmd[7].toInt().and(0x0f)
                    BtDevUnitList[id].muteSpkr = msg.btCmd[7].toInt().and(0x10) == 0x10
                    BtDevUnitList[id].muteMic = msg.btCmd[7].toInt().and(0x20) == 0x20
                    Logger.d(LogMain, "${String.format("src:%02X get hfp state %08X", msg.btCmd[2], BtDevUnitList[id].stateCon)}")
                }
                CmdId.GET_HFP_EXT_STA_RSP.value -> {
                    BtDevUnitList[id].stateExtra =
                        msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff)
                    BtDevUnitList[id].batLevel = BtDevUnitList[id].stateExtra.shr(12)
                    BtDevUnitList[id].batInd =
                        if (BtDevUnitList[id].stateExtra.and(0x40) == 0x40)
                            true
                        else
                            false
                    Logger.d(LogMain, "${String.format("src:%02X get hfp extra state", msg.btCmd[2])}")
                }
                CmdId.GET_HFP_VOL_RSP.value -> {
                    BtDevUnitList[id].volSpkrHfp = msg.btCmd[6].toInt().and(0x0f)
                    BtDevUnitList[id].muteSpkr = msg.btCmd[6].toInt().and(0x80) == 0x80
                    BtDevUnitList[id].muteMic = msg.btCmd[6].toInt().and(0x40) == 0x40
                    // viewPagerM6.setCurrentItem(0)
                    Logger.d(LogMain, "${String.format("src %02X get hfp vol %X", msg.btCmd[2], msg.btCmd[6])}")
                }
                CmdId.GET_HFP_RSSI_RSP.value -> {
                    BtDevUnitList[id].rssi = (0x10000 - (msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff))).and(0xffff)
                }
                CmdId.GET_HFP_PSKEY_RSP.value -> {
                    val pskId = msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff)
                    Logger.d(LogMain, "${String.format("get hfp pskey id:%d", pskId)}")
                    when (pskId) {
                        9 -> {
                            BtDevUnitList[id].featureMode = msg.btCmd[8].toInt().and(0xff).shl(24) + msg.btCmd[9].toInt().and(0xff).shl(16) + msg.btCmd[10].toInt().and(0xff).shl(8) + msg.btCmd[11].toInt().and(0xff)
                        }
                        16 -> {

                        }
                        17 -> {
                            BtDevUnitList[id].modeSrcWireHfpMicVol = msg.btCmd[8].toInt()
                            BtDevUnitList[id].modeSrcWireHfpSpkrVol = msg.btCmd[9].toInt()
                            BtDevUnitList[id].modeSrcUsbHfpMicVol = msg.btCmd[10].toInt()
                            BtDevUnitList[id].modeSrcUsbHfpSpkrVol = msg.btCmd[11].toInt()
                            BtDevUnitList[id].modeSrcBtHfpMicVol = msg.btCmd[12].toInt()
                            BtDevUnitList[id].modeSrcBtHfpSpkrVol = msg.btCmd[13].toInt()
                            BtDevUnitList[id].modeSrcVcsHfpMicVol = msg.btCmd[14].toInt()
                            BtDevUnitList[id].modeSrcVcsHfpSpkrVol = msg.btCmd[15].toInt()
                            BtDevUnitList[id].modeSrcWireAvSpkrVol = msg.btCmd[16].toInt()
                            BtDevUnitList[id].modeSrcUsbAvSpkrVol = msg.btCmd[17].toInt()
                            BtDevUnitList[id].modeSrcBtAvSpkrVol = msg.btCmd[18].toInt()
                            BtDevUnitList[id].modeSrcVcsAvSpkrVol = msg.btCmd[19].toInt()
                            BtDevUnitList[id].modeSrcSpkrDecade = msg.btCmd[20].toInt()
                        }
                        18 -> {
                            BtDevUnitList[id].modeAgWireHfpMicVol = msg.btCmd[8].toInt()
                            BtDevUnitList[id].modeAgWireHfpSpkrVol = msg.btCmd[9].toInt()
                            BtDevUnitList[id].modeAgUsbHfpMicVol = msg.btCmd[10].toInt()
                            BtDevUnitList[id].modeAgUsbHfpSpkrVol = msg.btCmd[11].toInt()
                            BtDevUnitList[id].modeAgBtHfpMicVol = msg.btCmd[12].toInt()
                            BtDevUnitList[id].modeAgBtHfpSpkrVol = msg.btCmd[13].toInt()
                            BtDevUnitList[id].modeAgVcsHfpMicVol = msg.btCmd[14].toInt()
                            BtDevUnitList[id].modeAgVcsHfpSpkrVol = msg.btCmd[15].toInt()
                            BtDevUnitList[id].modeAgWireAvSpkrVol = msg.btCmd[16].toInt()
                            BtDevUnitList[id].modeAgUsbAvSpkrVol = msg.btCmd[17].toInt()
                            BtDevUnitList[id].modeAgBtAvSpkrVol = msg.btCmd[18].toInt()
                            BtDevUnitList[id].modeAgVcsAvSpkrVol = msg.btCmd[19].toInt()
                        }
                        26 -> {
                            var str = ""

                            for (i in 0 until (msg.btCmd[5] - 2) / 2) {
                                str += msg.btCmd[i * 2 + 8].toInt().shl(8).or(msg.btCmd[i * 2 + 8 + 1].toInt()).toChar()
                            }
                            if (str.length == 0)
                                str = "alias name"
                            BtDevUnitList[id].nameAlias = str
                            Logger.d(LogMain, "${String.format("get ag pskey id:%d alias name:%s length:%d", pskId, str, str.length)}")
                        }
                        else -> {

                        }
                    }
                }
                CmdId.GET_AG_PSKEY_RSP.value -> {
                    val pskId = msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff)
                    Logger.d(LogMain, "${String.format("get ag pskey id:%d", pskId)}")
                }
                CmdId.GET_HFP_VRESION_RSP.value -> {
                    BtDevUnitList[id].verFirmwareHfp = String(msg.btCmd, 6, msg.btCmd[5].toInt())
                }
                CmdId.GET_AG_VRESION_RSP.value -> {
                    BtDevUnitList[id].verFirmwareAg = String(msg.btCmd, 6, msg.btCmd[5].toInt())
                    if (id.and(0x1) == 0x1)
                        BtDevUnitList[id + 1].verFirmwareAg = String(msg.btCmd, 6, msg.btCmd[5].toInt())
                }
                CmdId.GET_HFP_LOCAL_NAME_RSP.value -> {
                    BtDevUnitList[id].nameLocalHfp = String(msg.btCmd, 6, msg.btCmd[5].toInt())
                }
                CmdId.GET_AG_LOCAL_NAME_RSP.value -> {
                    BtDevUnitList[id].nameLocalAg = String(msg.btCmd, 6, msg.btCmd[5].toInt())
                    if (id.and(0x1) == 0x1)
                        BtDevUnitList[id + 1].nameLocalAg = String(msg.btCmd, 6, msg.btCmd[5].toInt())
                }
                CmdId.GET_SRC_DEV_NO_RSP.value -> {
                    Logger.d(LogMain, "${String.format("src:%02X source device number:%02d", msg.btCmd[2], msg.btCmd[6])}")
                    if (msg.btCmd[2] == 0x30.toByte()) {
                        initHfpDevice(msg.btCmd[6].toUByte().toInt())
                        BtDevUnit.deviceNo = msg.btCmd[6].toUByte().toInt()
                    }
                    setUpdate()
                    stateUpdate()
                }
                CmdId.GET_HFP_FEATURE_RSP.value -> {
                    BtDevUnitList[id].featureHfp = msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff)
                    BtDevUnit.maxAgNo = msg.btCmd[8].toInt()
                    BtDevUnit.maxTalkNo = msg.btCmd[9].toInt()
                    BtDevUnitList[id].bdaddrFilterHfp = bdaddrTranslate(msg, 11)
                    for (i in 0 until 4) {
                        BtDevUnitList[id].ledLightHfp[i] = msg.btCmd[17 + i * 2].toUByte().toInt().shl(8) + msg.btCmd[17 + i * 2 + 1].toUByte().toInt()
                    }
                    Logger.d(LogMain, "${String.format("src:%02X source feature:%04X", msg.btCmd[2], BtDevUnitList[id].featureHfp)}")
                }
                CmdId.GET_AG_FEATURE_RSP.value -> {
                    BtDevUnitList[id].featureAg = msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff)
                    BtDevUnitList[id].bdaddrFilterAg = bdaddrTranslate(msg, 11)
                    for (i in 0 until 4) {
                        BtDevUnitList[id].ledLightAg[i] = msg.btCmd[17 + i * 2].toUByte().toInt().shl(8) + msg.btCmd[17 + i * 2 + 1].toUByte().toInt()
                    }
                    if (id.and(0x1) == 0x1) {
                        BtDevUnitList[id + 1].featureAg = msg.btCmd[6].toInt().and(0xff).shl(8) + msg.btCmd[7].toInt().and(0xff)
                        BtDevUnitList[id + 1].bdaddrFilterAg = bdaddrTranslate(msg, 11)
                        for (i in 0 until 4) {
                            BtDevUnitList[id + 1].ledLightAg[i] = msg.btCmd[17 + i * 2].toUByte().toInt().shl(8) + msg.btCmd[17 + i * 2 + 1].toUByte().toInt()
                        }
                    }
                    Logger.d(LogMain, "${String.format("src:%02X source feature:%04X", msg.btCmd[2], BtDevUnitList[id].featureAg)}")
                }
                CmdId.GET_HFP_PAIR_RSP.value -> {
                    BtDevUnitList[id].bdaddrPair = bdaddrTranslate(msg, 7)
                }
                CmdId.GET_HFP_BDA_RSP.value -> {
                    if(id == 0)
                        BtDevUnitList[id].bdaddr = bdaddrTranslate(msg, 7)
                }
                CmdId.GET_AG_BDA_RSP.value -> {
                    BtDevUnitList[id].bdaddr = bdaddrTranslate(msg, 7)
                    if (id.and(0x1) == 0x1)
                        BtDevUnitList[id + 1].bdaddr = bdaddrTranslate(msg, 7)
                }
                CmdId.SET_DISCOVERY_RSP.value -> {
                    var str = ""

                    when(msg.btCmd[6]) {
                        0x00.toByte() -> {
                            Logger.d(LogMain, "discovery finished")
                        }
                        0x01.toByte() -> {
                            str += bdaddrTranslate(msg, 7)
                            Logger.d(LogMain, "discovery BDA: $str")
                            Logger.d(LogMain, "discovery eir type: ${msg.btCmd[13].toString(16)}")
                            str = ""
                            for(i: Int in 0 until msg.btCmd[5] - 8) {
                                str += msg.btCmd[14 + i].toChar().toString()
                            }
                            Logger.d(LogMain, "discovery local name: $str")
                        }
                        0x02.toByte() -> {
                            Logger.d(LogMain, "discovery start")
                        }
                        else -> {

                        }
                    }
                }
                CmdId.SET_INT_SERVICE_RSP.value ->
                    when (msg.btCmd[6]) {
                        0x00.toByte() -> {
                            var strList: List<String>
                            var sendMsg = BtDevMsg(0, 1)
                            var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                            editTextStaUpTime.clearFocus()
                            imm.hideSoftInputFromWindow(editTextStaUpTime.getWindowToken(), 0)
                            txvConSta0.text = applicationContext.resources.getString(R.string.txvStaEnable)
                            strList = preferData.getString("bdaddr0", "00:00:00:00:00:00")!!.split(':')
                            if(preferData.getString("bdaddr0", "00:00:00:00:00:00") == "00:00:00:00:00:00") {
                                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                                sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                                sendMsg.btCmd[4] = CmdId.SET_INT_DISCOVERY_REQ.value
                                sendMsg.btCmd[5] = 0x01
                                sendMsg.btCmd[6] = 0x01
                                sendBtServiceMsg(sendMsg)
                                for(i in 0 until ViewPagerArray.size) {
                                    if(ViewPagerArray[i] is FragmentPairSet) {
                                        viewPagerM6.setCurrentItem(i)
                                        break
                                    }
                                }
                            }
                            else {
                                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                                sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                                sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                                sendMsg.btCmd[5] = 0x07
                                sendMsg.btCmd[6] = 0x01
                                sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
                                sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
                                sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
                                sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
                                sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
                                sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
                                Logger.d(LogGbl, "bdaddr ${bdaddrTranslate(sendMsg, 7)}")
                                sendBtServiceMsg(sendMsg)
                            }
                            viewM6UpdateNest()
/*
                            for (i in 0 until MaxBtDev) {
                                var strList: List<String>
                                var sendMsg = BtDevMsg(0, 1)

                                strList = preferData.getString("bdaddr${i}", "00:00:00:00:00:00")!!.split(':')
                                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                                sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                                sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                                sendMsg.btCmd[5] = 0x07
                                sendMsg.btCmd[6] = 0x01
                                sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
                                sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
                                sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
                                sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
                                sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
                                sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
                                Logger.d(LogGbl, "bdaddr ${bdaddrTranslate(sendMsg, 7)}")
                                sendMsg.btDevNo = i
                                sendBtServiceMsg(sendMsg)
                            }
 */
                        }
                        STATE_ON.toByte(), STATE_OFF.toByte() -> {
                            finish()
                        }
                    }
                CmdId.SET_INT_CON_RSP.value -> when (msg.btDevNo) {
                    0 -> {
                        BtDevUnit.sppStateCon = msg.btCmd[6]
                        txvConSta0.text =
                            when (BtDevUnit.sppStateCon) {
                                0x00.toByte() -> {
                                    var sendMsg = BtDevMsg(0, 0)

                                    // viewPagerM6.visibility = VISIBLE
                                    BtDevUnit.isReconnect = true
                                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                                    sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
                                    sendMsg.btCmd[4] = CmdId.GET_SRC_DEV_NO_REQ.value
                                    sendMsg.btCmd[5] = 0x01
                                    sendMsg.btCmd[6] = 0x00
                                    sendBtServiceMsg(sendMsg)
                                    for (i in 0 until ViewPagerArray.size) {
                                        if (ViewPagerArray[i] is FragmentConState) {
                                            viewPagerM6.setCurrentItem(i)
                                            break
                                        }
                                    }
                                    applicationContext.resources.getString(R.string.txvStaConnected)
                                }
                                0x01.toByte() -> {
                                    var strList: List<String>

                                    // viewPagerM6.visibility = INVISIBLE
                                    if (BtDevUnit.isReconnect == true) {
                                        BtDevUnit.isReconnect = false
                                        for (i in 0 until BtDevUnit.MaxBtDev) {
                                            var sendMsg = BtDevMsg(0, 1)

                                            strList = preferData.getString(
                                                "bdaddr${i}",
                                                "00:00:00:00:00:00"
                                            )!!.split(':')
                                            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                                            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                                            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                                            sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                                            sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                                            sendMsg.btCmd[5] = 0x07
                                            sendMsg.btCmd[6] = 0x01
                                            sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
                                            sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
                                            sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
                                            sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
                                            sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
                                            sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
                                            Logger.d(
                                                LogMain,
                                                "bdaddr ${bdaddrTranslate(sendMsg, 7)}"
                                            )
                                            sendMsg.btDevNo = i
                                            sendBtServiceMsg(sendMsg)
                                        }
                                        Logger.d(LogMain, "bluetooth reconnecct")
                                    }
                                    Logger.d(LogMain, "bluetooth disconnect")
                                    applicationContext.resources.getString(R.string.txvStaDiscon)
                                }
                                0x02.toByte() -> applicationContext.resources.getString(R.string.txvStaConnecting)
                                else -> applicationContext.resources.getString(R.string.txvStaEnable)
                            }
                    }
                    1 -> txvConSta1.text =
                        when (msg.btCmd[6]) {
                            0x00.toByte() -> applicationContext.resources.getString(R.string.txvStaConnected)
                            0x01.toByte() -> applicationContext.resources.getString(R.string.txvStaDiscon)
                            0x02.toByte() -> applicationContext.resources.getString(R.string.txvStaConnecting)
                            else -> applicationContext.resources.getString(R.string.txvStaEnable)
                        }
                    else -> {
                    }
                }
                CmdId.SET_INT_DISCOVERY_RSP.value -> {
                    when (msg.btCmd[6]) {
                        0x00.toByte() -> {
                            BtDevUnit.PairState = 0
                        }
                        0x01.toByte() -> {
                            var str = ""

                            BtDevUnit.PairState = 1
                            for (i in 0 until (msg.btCmd[5] - 7) / 2) {
                                str += msg.btCmd[i * 2 + 13].toInt().shl(8).or(msg.btCmd[i * 2 + 13 + 1].toInt()).toChar()
                            }
                            str += " + " + bdaddrTranslate(msg, 7)
                            if(str.substring(0, 6).compareTo("iMage ") == 0) {
                                if(!BtDevUnit.BtList.contains(str))
                                BtDevUnit.BtList.add(str)
                                Logger.d(LogMain, "$str has iMage")
                            }
                        }
                        0x02.toByte() -> {
                            BtDevUnit.PairState = 2
                            BtDevUnit.BtList.removeAll(BtDevUnit.BtList)
                            BtDevUnit.BtList.add("clear paired device + 00:00:00:00:00:00")
                        }
                    }
                    Logger.d(LogMain, " cmmmand SET_INT_DISCOVERY_RSP")
                }
                CmdId.SET_INT_PAIR_RSP.value -> {
                    var str = ""

                    BtDevUnit.PairState =
                        when(msg.btCmd[6]) {
                            0x00.toByte() -> 0
                            0x01.toByte() -> 3
                            0x02.toByte() -> {
                                BtDevUnit.BtList.removeAll(BtDevUnit.BtList)
                                BtDevUnit.BtList.add("clear paired device + 00:00:00:00:00:00")
                                3
                            }
                            else -> 0
                        }
                    for (i in 0 until (msg.btCmd[5] - 7) / 2) {
                        str += msg.btCmd[i * 2 + 13].toInt().shl(8).or(msg.btCmd[i * 2 + 13 + 1].toInt()).toChar()
                    }
                    str += " + " + bdaddrTranslate(msg, 7)
                    if(str.substring(0, 6).compareTo("iMage ") == 0) {
                        BtDevUnit.BtList.add(str)
                        Logger.d(LogMain, "$str has iMage")
                    }
                    Logger.d(LogMain, "command SET_INT_PAIR_RSP")
                }
                else -> {
                    Logger.d(LogMain, "${String.format("other command src:%02X id:%02X", msg.btCmd[2], msg.btCmd[4])}")
                }
            }
            viewM6Update()
            // ((viewPagerM6.adapter as ViewPagerAdapter).getItem(0) as FragmentConState).recyclerDevList.adapter!!.notifyDataSetChanged()
            // viewPagerM6.adapter!!.notifyDataSetChanged()
        }
    }
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = ViewPagerArray[position]
    override fun getCount(): Int = ViewPagerArray.size
}