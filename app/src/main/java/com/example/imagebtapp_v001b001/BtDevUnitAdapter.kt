package com.example.imagebtapp_v001b001

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Movie
import android.graphics.Movie.decodeFile
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.device_unit_adapter.view.*
import java.io.File
import java.io.IOException
import java.io.InputStream

class BtDevUnitAdapter(val btDevUnitList: ArrayList<BtDevUnit>, val strIndicate: Array<String>) : RecyclerView.Adapter<BtDevUnitAdapter.ViewHolder>() {
    private val rssiStrong = 50
    private val rssiWeak = 90
    lateinit var clickItemImgListener: OnItemImageListener
    lateinit var longClickItemImgListener: OnLongItemImageLisener
    lateinit var clickSpkrVolListener: OnSpkrVolListener
    lateinit var clickSpkrMuteListener: OnSpkrMuteListener
    lateinit var longClickSpkrMuteListener: OnLongSpkrMuteListener
    lateinit var clickMicMuteListener: OnMicMuteListener
    lateinit var longClickMicMuteListener: OnLongMicMuteListener
    lateinit var clickTalkListener: OnTalkListener
    lateinit var longClickTalkListener: OnLongTalkListener
    lateinit var longClickNameListener: OnLongNameListener
    val imgIconId = arrayOf(R.drawable.android_image_1, R.drawable.android_image_2, R.drawable.android_image_3, R.drawable.android_image_4, R.drawable.android_image_5, R.drawable.android_image_6, R.drawable.android_image_7, R.drawable.android_image_8)

    interface OnItemImageListener {
        fun onItemImage(position: Int, btDevUnit: BtDevUnit)
    }
    fun setOnkItemImageListener(listen: OnItemImageListener) {
        this.clickItemImgListener = listen
    }

    interface OnLongItemImageLisener {
        fun onLongItemImage(position: Int, btDevUnit: BtDevUnit)
    }
    fun setOnLongItemImageListener(listen: OnLongItemImageLisener) {
        this.longClickItemImgListener = listen
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
        this.longClickTalkListener = listen
    }

    interface OnLongNameListener {
        fun onLongNameEdit(position: Int, btDevUnit: BtDevUnit)
    }
    fun setOnLongNameEditListener(listen: OnLongNameListener) {
        this.longClickNameListener = listen
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
            imgViewIcon.setImageResource(R.drawable.android_image_1)
            imgViewIcon.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    clickItemImgListener.onItemImage(adapterPosition, btDevUnitList[adapterPosition])
                }
            })
            imgViewIcon.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longClickItemImgListener.onLongItemImage(adapterPosition, btDevUnitList[adapterPosition])
                    return true
                }
            })
            imgViewSpkrMute.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    clickSpkrMuteListener.onSpkrMute(adapterPosition, btDevUnitList[adapterPosition].muteSpkr)
                }
            })
            imgViewSpkrMute.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longClickSpkrMuteListener.onLongSpkrMute(adapterPosition, btDevUnitList[adapterPosition].muteSpkr)
                    return true
                }
            })
            imgViewMicMute.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    clickMicMuteListener.onMicMute(adapterPosition, btDevUnitList[adapterPosition].muteMic)
                }

            })
            imgViewMicMute.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longClickMicMuteListener.onLongMicMute(adapterPosition, btDevUnitList[adapterPosition].muteMic)
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
                    longClickTalkListener.onLongTalk(adapterPosition)
                    return true
                }
            })
            nameTxv.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    longClickNameListener.onLongNameEdit(adapterPosition, btDevUnitList[adapterPosition])
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
        }
    }

    override fun getItemCount(): Int = btDevUnitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.nameTxv.text = btDevUnitList[position].nameLocalHfp
        holder.nameTxv.text = btDevUnitList[position].nameAlias
        holder.itemView.seekVolSpkr.seekVolSpkr.progress = btDevUnitList[position].volSpkrHfp
        holder.itemView.seekVolMic.seekVolMic.progress = btDevUnitList[position].volMicHfp
        // holder.itemView.imgViewMuSpkr.visibility = VISIBLE
        // holder.itemView.imgViewMuSpkr.setImageResource(android.R.drawable.ic_lock_silent_mode_off)
        Glide.with(holder.imgViewIcon.context).load(btDevUnitList[position].imgIconUri).error(imgIconId[position % imgIconId.size]).into(holder.imgViewIcon)
        Logger.d(LogGbl, "imgIconUri$position: ${btDevUnitList[position].imgIconUri}")
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
            // holder.nameTxv.visibility  = VISIBLE
            // holder.imgViewIcon.visibility = VISIBLE
        }
        else {
            holder.batTxv.visibility = INVISIBLE
            holder.rssiTxv.visibility = INVISIBLE
            // holder.nameTxv.visibility  = INVISIBLE
            // holder.imgViewIcon.visibility = INVISIBLE
        }
        if(btDevUnitList[position].stateCon.and(0x00000080) == 0x00000080) {
            holder.seekMicVol.visibility = INVISIBLE
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

class SpacesItemDecoration(var spaces: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = spaces
        outRect.right = spaces
    }
}
