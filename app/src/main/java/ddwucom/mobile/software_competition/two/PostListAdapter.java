package ddwucom.mobile.software_competition.two;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ddwucom.mobile.software_competition.R;

public class PostListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<PostDTO> postList;
    private LayoutInflater inflater;
    private PostDTO tmp;

    public PostListAdapter(Context context, int layout, ArrayList<PostDTO> postList) {
        this.context = context;
        this.layout = layout;
        this.postList = postList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return postList.size(); }

    @Override
    public Object getItem(int pos) { return postList.get(pos); }

    @Override
    public long getItemId(int pos) { return postList.get(pos).get_id(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvPostListTitle = convertView.findViewById(R.id.tvPostListTitle);
            viewHolder.tvPostListNickname = convertView.findViewById(R.id.tvPostListNickname);
            viewHolder.tvPostListCreateDate = convertView.findViewById(R.id.tvPostListCreateDate);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        tmp = postList.get(pos);

        viewHolder.tvPostListTitle.setText(tmp.getTitle());
        if (tmp.getAnonymous() == 1) {
            viewHolder.tvPostListNickname.setText("익명");
        } else {
            viewHolder.tvPostListNickname.setText(tmp.getNickname());
        }
        viewHolder.tvPostListCreateDate.setText(tmp.getCreateDate());

        //공지사항이면 배경색 넣기
        try {
            if (tmp.getCategory().equals("공지사항")) {
                convertView.setBackgroundColor(Color.LTGRAY);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }
        } catch (Exception e) {
            Log.d("MAINCC", e.toString());
            e.printStackTrace();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvPostListTitle;
        TextView tvPostListNickname;
        TextView tvPostListCreateDate;
    }
}

