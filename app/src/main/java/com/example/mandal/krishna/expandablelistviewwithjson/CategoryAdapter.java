package com.example.mandal.krishna.expandablelistviewwithjson;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc41 on 17-10-2017.
 */

public class CategoryAdapter implements ExpandableListAdapter {

    private Context context;
    private List<CategoryPojo> categoryPojos = new ArrayList<CategoryPojo>();
    List<SubCategoryPojo> subCategoryPojolist=new ArrayList<>();

    public CategoryAdapter(Context context, List<CategoryPojo> categoryPojos) {
        this.context = context;
        this.categoryPojos = categoryPojos;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return categoryPojos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

       subCategoryPojolist = categoryPojos.get(groupPosition).getSubCategoryPojos();
        return subCategoryPojolist.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryPojos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return categoryPojos.get(groupPosition).getSubCategoryPojos().get(childPosition);
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
        ParentHolder parentHolder = null;
        CategoryPojo categoryPojo = (CategoryPojo) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater userInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = userInflater.inflate(R.layout.category_child, null);
            convertView.setHorizontalScrollBarEnabled(true);
            parentHolder = new ParentHolder(convertView);
            convertView.setTag(parentHolder);
        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }
        parentHolder.categoryName.setText("    "+categoryPojo.getTitle());
        if(isExpanded) {
            parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
            parentHolder.categoryName.setBackgroundResource(R.color.colorGray);
        } else {
            parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
            parentHolder.categoryName.setBackgroundResource(R.color.colorWhite);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
       SubCategoryPojo subCategoryPojo = (SubCategoryPojo) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sub_category_child, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.subCategoryTitle.setText(subCategoryPojo.getTitle().trim());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true ;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private static class ParentHolder {
        TextView categoryName;
        ImageView indicator;
        ParentHolder(View view){
            categoryName = (TextView) view.findViewById(R.id.main_category);
            indicator = (ImageView) view.findViewById(R.id.image_indicator);
        }
    }
    private static class ChildHolder {
        TextView subCategoryTitle;
        ChildHolder(View convertView) {
            subCategoryTitle= (TextView) convertView.findViewById(R.id.sub_category_title);
        }
    }
}
