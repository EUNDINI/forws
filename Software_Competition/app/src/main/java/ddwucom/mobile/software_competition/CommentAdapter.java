package ddwucom.mobile.software_competition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private int layout;
    private ArrayList<CommentDTO> myCommentDataList;
    private LayoutInflater inflater;

    public CommentAdapter(Context context, int layout, ArrayList<CommentDTO> myCommentDataList) {
        this.layout = layout;
        this.myCommentDataList = myCommentDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myCommentDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return myCommentDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myCommentDataList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView ncikname = convertView.findViewById(R.id.nickname);
        TextView contents = convertView.findViewById(R.id.comment);
        TextView createDate = convertView.findViewById(R.id.createDate);

        if(myCommentDataList.get(pos).getAnonymous() == 0)
            ncikname.setText(myCommentDataList.get(pos).getNickname());
        else
            ncikname.setText("익명");
        contents.setText(myCommentDataList.get(pos).getCommentBody());
        createDate.setText(myCommentDataList.get(pos).getCreateDate());

        return convertView;
    }
}
