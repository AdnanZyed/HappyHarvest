//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.happyharvest.FertilizingSchedule;
//
//import java.util.List;
//
//public class ScheduleAdapter extends BaseAdapter {
//    private List<FertilizingSchedule> items;
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_schedule, parent, false);
//        }
//
//        FertilizingSchedule item = getItem(position);
//
//        TextView date = convertView.findViewById(R.id.text_date);
//        TextView type = convertView.findViewById(R.id.text_type);
//        TextView amount = convertView.findViewById(R.id.text_amount);
//
//        date.setText(item.getDate());
//        type.setText(item.getFertilizerType());
//        amount.setText(String.format("%.1f غم", item.getAmount()));
//
//        return convertView;
//    }
//}