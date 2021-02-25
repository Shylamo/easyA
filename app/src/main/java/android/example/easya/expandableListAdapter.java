package android.example.easya;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class expandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String>  en_tete;//ListdataHeader
    private HashMap<String,List<String>> listHashMap;

    public expandableListAdapter(Context context, List<String> en_tete, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.en_tete = en_tete;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return en_tete.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(en_tete.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return en_tete.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(en_tete.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String en_tete_titre= (String) getGroup(groupPosition);
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.list_group,null);
        }
        TextView grp=(TextView) convertView.findViewById(R.id.list_group);
        grp.setTypeface(null, Typeface.BOLD_ITALIC);
        grp.setText(en_tete_titre);
        return  convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String child_text=(String) getChild(groupPosition,childPosition);
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.sous_list_group,null);
        }
        TextView sous_grp=(TextView) convertView.findViewById(R.id.sous_grp);
        sous_grp.setText(child_text);
        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
