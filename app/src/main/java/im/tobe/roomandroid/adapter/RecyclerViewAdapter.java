package im.tobe.roomandroid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import im.tobe.roomandroid.R;
import im.tobe.roomandroid.model.Contact;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private OnContactClickedListener onContactClickedListener;
    private List<Contact> contactList;
    private Context context;

    public RecyclerViewAdapter(List<Contact> contactList, Context context, OnContactClickedListener onContactClickedListener) {
        this.contactList = contactList;
        this.context = context;
        this.onContactClickedListener = onContactClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);

        return new ViewHolder(view, this.onContactClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.getRowName().setText(contact.getName());
        holder.getRowOccupation().setText(contact.getOccupation());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // for each row
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnContactClickedListener onContactClickedListener;
        private TextView rowName;
        private TextView rowOccupation;

        public ViewHolder(@NonNull View itemView, OnContactClickedListener onContactClickedListener) {
            super(itemView);
            rowName = itemView.findViewById(R.id.rowNameText);
            rowOccupation = itemView.findViewById(R.id.rowOccupationText);
            this.onContactClickedListener = onContactClickedListener;

            itemView.setOnClickListener(this);
        }

        public TextView getRowName() {
            return rowName;
        }

        public TextView getRowOccupation() {
            return rowOccupation;
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: inside");
            onContactClickedListener.onContactClicked(getAdapterPosition());
        }
    }

    public interface OnContactClickedListener {
        void onContactClicked(int position);
    }
}
