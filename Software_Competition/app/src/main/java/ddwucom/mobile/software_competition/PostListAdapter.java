package ddwucom.mobile.software_competition;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<PostDTO> postList;
    private LayoutInflater inflater;
    private PostDTO tmp; //공지사항이면 색을 넣기 위함

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
            viewHolder.tvPostListCreateTime = convertView.findViewById(R.id.tvPostListCreateTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvPostListTitle.setText(postList.get(pos).getTitle());
        if (postList.get(pos).getAnonymous() == 1) {
            viewHolder.tvPostListNickname.setText("익명");
        } else {
            viewHolder.tvPostListNickname.setText(postList.get(pos).getNickname());
        }
        viewHolder.tvPostListCreateTime.setText(postList.get(pos).getCreateDate());

   /*     //공지사항이면 배경색 넣기
        tmp = postList.get(pos);
        if (tmp != null) {
            if (tmp.getCategory().equals("공지사항")) {
                convertView.setBackgroundColor(Color.LTGRAY);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }
        }*/

        return convertView;
    }

    static class ViewHolder {
        TextView tvPostListTitle;
        TextView tvPostListNickname;
        TextView tvPostListCreateTime;
    }
}
