import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cat.CatData
import com.example.cat.R
class CatAdapter(
    private val catList: List<CatData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem = catList[position]
        // Set the data to the views in the item layout
        holder.itemView.findViewById<TextView>(R.id.show_title).text = currentItem.title
        holder.itemView.findViewById<TextView>(R.id.show_description).text = currentItem.description
        holder.itemView.findViewById<TextView>(R.id.show_date).text = currentItem.date
        Glide.with(holder.itemView.context).load(currentItem.imageUri).into(holder.itemView.findViewById(R.id.itm_img))

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }

        // Share button click listener
        holder.itemView.findViewById<Button>(R.id.shareButton).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cat: ${currentItem.title}\n\n${currentItem.imageUri}")
            holder.itemView.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
