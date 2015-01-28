package cpe.phaith.androidfundamental.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cpe.phaith.androidfundamental.R;
import cpe.phaith.androidfundamental.items.PhoneItem;

/**
 * Created by jerry on 28/1/2558.
 */
public class PhoneListAdapter extends BaseAdapter {

    private Context context;
    private List<PhoneItem> phoneList;

    public PhoneListAdapter(Context context, List<PhoneItem> phoneList) {
        this.context = context;
        this.phoneList = phoneList;

    }

    @Override
    public int getCount() {
        return phoneList.size();
    }

    @Override
    public PhoneItem getItem(int position) {
        return phoneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView phoneImage;
        TextView phoneName;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.phone_item, parent, false);
            phoneImage = (ImageView)convertView.findViewById(R.id.phoneImage);
            phoneName = (TextView)convertView.findViewById(R.id.txtPhoneName);
            convertView.setTag(new ViewHolder(phoneImage, phoneName));
        }else{
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            phoneImage = viewHolder.phoneImage;
            phoneName = viewHolder.phoneName;
        }
        PhoneItem phoneItem = getItem(position);
        phoneName.setText(phoneItem.getName());
        return convertView;
    }

    private static class ViewHolder {
        public final ImageView phoneImage;
        public final TextView phoneName;

        public ViewHolder(ImageView phoneImage, TextView phoneName) {
            this.phoneImage = phoneImage;
            this.phoneName = phoneName;
        }
    }
}
