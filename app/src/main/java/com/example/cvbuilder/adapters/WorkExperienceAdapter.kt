package com.example.cvbuilder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilder.R
import com.example.cvbuilder.models.Work
import com.google.android.material.imageview.ShapeableImageView

class WorkExperienceAdapter(private val workList: ArrayList<Work>): RecyclerView.Adapter<WorkExperienceAdapter.WorkExperiencesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkExperiencesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.work_list_item, parent, false)
        return WorkExperiencesHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkExperiencesHolder, position: Int) {
        val currentItem = workList[position]
        holder.workImage.setImageResource(currentItem.image)
        holder.heading.text = currentItem.title
        if(currentItem.client != "") {
            holder.company.text = currentItem.company + " at client " + currentItem.client
        } else {
            holder.company.text = currentItem.company
        }
        holder.dates.text = currentItem.startDate + " - " + currentItem.endDate
    }

    override fun getItemCount(): Int {
        return workList.size
    }

    class WorkExperiencesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val workImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val heading: TextView = itemView.findViewById(R.id.heading)
        val company: TextView = itemView.findViewById(R.id.company)
        val dates: TextView = itemView.findViewById(R.id.date)
    }
}