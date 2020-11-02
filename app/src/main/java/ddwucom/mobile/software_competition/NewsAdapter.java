package ddwucom.mobile.software_competition;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    private static final String TAG = "MyAdapter";

    private Context context;
    private int layout;
    private ArrayList<NewsDTO> policyNewsDataList;
    private LayoutInflater inflater;

    public NewsAdapter(Context context, int layout, ArrayList<NewsDTO> myDataList) {
        this.context = context;
        this.layout = layout;
        this.policyNewsDataList = myDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return policyNewsDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return policyNewsDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return policyNewsDataList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView regDt = convertView.findViewById(R.id.regDt);
        TextView title = convertView.findViewById(R.id.title);

        String url = policyNewsDataList.get(pos).getThumbUrl();
        Log.d("TAG", url);

        regDt.setText(policyNewsDataList.get(pos).getRegDt());
        title.setText(policyNewsDataList.get(pos).getTitle());

        return convertView;
    }
}
