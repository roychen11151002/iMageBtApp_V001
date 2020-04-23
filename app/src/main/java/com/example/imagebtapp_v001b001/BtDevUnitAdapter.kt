package com.example.imagebtapp_v001b001

import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.persistableBundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.device_unit_adapter.view.*

class BtDevUnitAdapter(val btDevUnitList: ArrayList<BtDevUnit>, val strIndicate: Array<String>) : RecyclerView.Adapter<BtDevUnitAdapter.ViewHolder>() {
    private val rssiStrong = 50
    private val rssiWeak = 90
    lateinit var clickItemClickListener: OnItemClickListener
    lateinit var clickSpkrVolListener: OnSpkrVolListener
    lateinit var clickSpkrMuteListener: OnSpkrMuteListener
    lateinit var longClickSpkrMuteListener: OnLongSpkrMuteListener
    lateinit var clickMicMuteListener: OnMicMuteListener
    lateinit var longClickMicMuteListener: OnLongMicMuteListener
    lateinit var clickTalkListener: OnTalkListener
    lateinit var longclickTalkListener: OnLongTalkListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, btDevUnit: BtDevUnit)
    }
    fun setOnItemClickListener(listen: OnItemClickListener) {
        this.clickItemClickListener = listen
    }

    interface OnSpkrVolListener {
        fun onSpkrVol(position: Int, progress: Int, fromUser: Boolean)
    }
    fun setOnSpkrVolListener(listen: OnSpkrVolListener) {
        this.clickSpkrVolListener = listen
    }

    interface OnSpkrMuteListener {
        fun onSpkrMute(position: Int, mute: Boolean)
    }
    fun setOnSpkrMuteListener(listen: OnSpkrMuteListener) {
        this.clickSpkrMuteListener = listen
    }

    interface OnLongSpkrMuteListener {
        fun onLongSpkrMute(position: Int, mute: Boolean)
    }
    fun setOnLongSpkrMuteListener(listen: OnLongSpkrMuteListener) {
        this.longClickSpkrMuteListener = listen
    }

    interface OnMicMuteListener {
        fun onMicMute(position: Int, mute: Boolean)
    }
    fun setOnMicMuteListener(listen: OnMicMuteListener) {
        this.clickMicMuteListener = listen
    }

    interface OnLongMicMuteListener {
        fun onLongMicMute(position: Int, mute: Boolean)
    }
    fun setOnLongMicMuteListener(listen: OnLongMicMuteListener) {
        this.longClickMicMuteListener = listen
    }

    interface OnTalkListener {
        fun onTalk(position: Int)
    }
    fun setOnTalkListener(listen: OnTalkListener) {
        this.clickTalkListener = listen
    }

    interface OnLongTalkListener {
        fun onLongTalk(position: Int)
    }
    fun setOnLongTalkListener(listen: OnLongTalkListener) {
        this.longclickTalkListener = listen
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTxv: TextView
        var batTxv: TextView
        var rssiTxv: TextView
        var imgViewIcon: ImageView
        var imgViewStaCon: ImageView
        var imgViewSpkrMute: ImageView
        var imgViewMicMute: ImageView
        var seekSpkrVol: SeekBar
        var seekMicVol: SeekBar

        init {
            imgViewIcon = itemView.findViewById(R.id.imgViewIcon)
            imgViewStaCon = itemView.findViewById(R.id.imgViewStaCon)
            imgViewSpkrMute = itemView.findViewById(R.id.imgViewMuSpkr)
            seekSpkrVol = itemView.findViewById(R.id.seekVolSpkr)
            imgViewMicMute = itemView.findViewById(R.id.imgViewMuMic)
            seekMicVol = itemView.findViewById(R.id.seekVolMic)
            nameTxv = itemView.findViewById(R.id.txvDeviceName)
            batTxv = itemView.findViewById(R.id.txvBat)
            rssiTxv = itemView.findViewById(R.id.txvRssi)
            imgViewIcon.setOnClickListener(View.OnClickListener {
                clickItemClickListener.onItemClick(adapterPosition, btDevUnitList[adapterPosition])
            })
            imgViewSpkrMute.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    clickSpkrMuteListener.onSpkrMute(
                        adapterPosition,
                        btDevUnitList[adapterPosition].muteSpkr
                    )
                }
            })
            imgViewSpkrMute.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longClickSpkrMuteListener.onLongSpkrMute(
                        adapterPosition,
                        btDevUnitList[adapterPosition].muteSpkr
                    )
                    return true
                }
            })
            imgViewMicMute.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    clickMicMuteListener.onMicMute(
                        adapterPosition,
                        btDevUnitList[adapterPosition].muteMic
                    )
                }

            })
            imgViewMicMute.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longClickMicMuteListener.onLongMicMute(
                        adapterPosition,
                        btDevUnitList[adapterPosition].muteMic
                    )
                    return true
                }
            })
            imgViewStaCon.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    clickTalkListener.onTalk(adapterPosition)
                }
            })
            imgViewStaCon.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longclickTalkListener.onLongTalk(adapterPosition)
                    return true
                }
            })
            seekSpkrVol.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    Logger.d(LogGbl, "seekVolSpkr progress changed $adapterPosition $fromUser")
                    clickSpkrVolListener.onSpkrVol(adapterPosition, progress, fromUser)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
            nameTxv.setOnClickListener {
                Logger.d(LogGbl, "position$adapterPosition name edit request")
            }
        }
    }

    override fun getItemCount(): Int = btDevUnitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTxv.text = btDevUnitList[position].localNameHfp
        // holder.nameTxv.text = btDevUnitList[position].nameAlias
        holder.itemView.seekVolSpkr.seekVolSpkr.progress = btDevUnitList[position].volSpkrHfp
        holder.itemView.seekVolMic.seekVolMic.progress = btDevUnitList[position].volMicHfp
        // holder.itemView.imgViewMuSpkr.visibility = VISIBLE
        // holder.itemView.imgViewMuSpkr.setImageResource(android.R.drawable.ic_lock_silent_mode_off)
        holder.imgViewIcon.setImageResource(
            when(position % 8) {
                0 -> R.drawable.android_image_1
                1 -> R.drawable.android_image_2
                2 -> R.drawable.android_image_3
                3 -> R.drawable.android_image_4
                4 -> R.drawable.android_image_5
                5 -> R.drawable.android_image_6
                6 -> R.drawable.android_image_7
                7 -> R.drawable.android_image_8
                else -> R.drawable.android_image_1
            })
        holder.imgViewStaCon.setBackgroundColor((
            if(btDevUnitList[position].stateCon.and(0x00000080) == 0x00000080)
                0xFF54A6E8
            else if(btDevUnitList[position].stateCon.and(0x00000400) == 0x00000400)
                0xFF53D658
            else if(btDevUnitList[position].stateCon.and(0x00000220) == 0x00000220)
                0xFFDFD581
            else
                0xFFD6D0D0).toInt())
        holder.itemView.imgViewMuSpkr.setBackgroundColor((
            if(btDevUnitList[position].muteSpkr == true)
                0x80FF0000
            else
                0x800000FF).toInt())
        holder.imgViewMicMute.setBackgroundColor((
            if(btDevUnitList[position].muteMic == true)
                0x80FF0000
            else
                0x800000FF).toInt())
        holder.batTxv.text =
            if(btDevUnitList[position].stateExtra.and(0x02) == 0x02)
                if(btDevUnitList[position].stateExtra.and(0x04) == 0x04)
                     String.format("%s%d ", strIndicate[4], btDevUnitList[position].batLevel)
                else
                    String.format("%s%d ", strIndicate[5], btDevUnitList[position].batLevel)
            else
                if(btDevUnitList[position].batInd)
                    String.format("%s%d ", strIndicate[2], btDevUnitList[position].batLevel)
                else
                    String.format("%s%d ", strIndicate[3], btDevUnitList[position].batLevel)
        holder.batTxv.setTextColor((
            if(btDevUnitList[position].batInd)
                0xff000000
            else
                0xffff0000).toInt())
        holder.rssiTxv.text =
            if(btDevUnitList[position].rssi <= rssiStrong) {
                String.format("%s9",strIndicate[0])
            }
            else if(btDevUnitList[position].rssi <= rssiWeak) {
                if((rssiWeak - btDevUnitList[position].rssi) * 9 / (rssiWeak - rssiStrong) < 3)
                    String.format("%s%d", strIndicate[1], (rssiWeak - btDevUnitList[position].rssi) * 9 / (rssiWeak - rssiStrong))
                else
                    String.format("%s%d", strIndicate[0], (rssiWeak - btDevUnitList[position].rssi) * 9 / (rssiWeak - rssiStrong))
            }
            else {
                String.format("%s0",strIndicate[1])
            }
        holder.rssiTxv.setTextColor((
            if((rssiWeak - btDevUnitList[position].rssi) * 9 / (rssiWeak - rssiStrong) < 3)
                0xffff0000
            else
                0xff000000).toInt())
        if(btDevUnitList[position].stateCon.and(0x00000220) == 0x00000220) {
            holder.batTxv.visibility = VISIBLE
            holder.rssiTxv.visibility = VISIBLE
            holder.nameTxv.visibility  = VISIBLE
            holder.imgViewIcon.visibility = VISIBLE
        }
        else {
            holder.batTxv.visibility = INVISIBLE
            holder.rssiTxv.visibility = INVISIBLE
            holder.nameTxv.visibility  = INVISIBLE
            holder.imgViewIcon.visibility = INVISIBLE
        }
        if(btDevUnitList[position].stateCon.and(0x00000080) == 0x00000080) {
            holder.seekMicVol.visibility = VISIBLE
            holder.seekSpkrVol.visibility = VISIBLE
            holder.imgViewMicMute.visibility = VISIBLE
            holder.imgViewSpkrMute.visibility = VISIBLE
        }
        else if(btDevUnitList[position].stateCon.and(0x00000400) == 0x00000400) {
            holder.seekMicVol.visibility = INVISIBLE
            holder.seekSpkrVol.visibility = VISIBLE
            holder.imgViewMicMute.visibility = INVISIBLE
            holder.imgViewSpkrMute.visibility = VISIBLE
        }
        else {
            holder.seekMicVol.visibility = INVISIBLE
            holder.seekSpkrVol.visibility = INVISIBLE
            holder.imgViewMicMute.visibility = INVISIBLE
            holder.imgViewSpkrMute.visibility = INVISIBLE
        }
        Logger.d(LogGbl, "btDevUnitAdapter on bind")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.device_unit_adapter, parent, false))
    }
}
