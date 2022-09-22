package com.dicoding.mynotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mynotesapp.R
import com.dicoding.mynotesapp.databinding.ItemNoteBinding
import com.dicoding.mynotesapp.entity.Note

class NoteAdapter(private val onItemClickCallback: OnItemClickCallBack) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    //variabel array list untuk data cardview
    var listNotes = ArrayList<Note>()
        set(listNotes) {
            if(listNotes.size > 0){
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(note: Note){
            binding.tvItemTitle.text = note.title
            binding.tvItemDate.text = note.date
            binding.tvItemDesc.text = note.description

            //listenenr click untuk mengarahkan user ke activity NoteAdd ketika cardview di klik
            binding.cvItemNote.setOnClickListener{
                onItemClickCallback.onItemClickCallBack(note, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = this.listNotes.size

    //method untuk menambah, memperbarui dan menghapus item di recycler view
    fun addItem(note: Note){
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note){
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int){
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    interface OnItemClickCallBack {
        fun onItemClickCallBack(selectedNote: Note?, position: Int)
    }
}