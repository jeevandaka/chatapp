import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Message
import com.example.chatapp.R
import java.util.Collections.addAll


class ChatBoxAdapter     // in this adaper constructor we add the list of messages as a parameter so that
// we will passe  it when making an instance of the adapter object in our activity
    (private val MessageList: ArrayList<Message>) :
    RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nickname: TextView
        var message: TextView

        init {
            nickname = view.findViewById(R.id.nickname1)
            message = view.findViewById(R.id.message1)
        }
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.chatitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //binding the data from our ArrayList of object to the item.xml using the viewholder
        val m = MessageList[position]
        holder.nickname.text = m.nickname + " : "
        holder.message.text = m.message
    }

    fun updateData(viewModels: ArrayList<Message>) {
        MessageList.clear()
        MessageList.addAll(viewModels)
        notifyDataSetChanged()
    }
}