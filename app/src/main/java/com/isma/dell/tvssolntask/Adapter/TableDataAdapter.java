package com.isma.dell.tvssolntask.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.isma.dell.tvssolntask.HomeActivity;
import com.isma.dell.tvssolntask.R;
import com.isma.dell.tvssolntask.SingleDataInfo;

import java.util.ArrayList;
import java.util.List;

public class TableDataAdapter extends RecyclerView.Adapter<TableDataAdapter.ViewHolder> implements Filterable {

    private List<List<String>> stringList=new ArrayList<>();
    private List<List<String>> stringListFiltered=new ArrayList<>();
    Context context;

    public TableDataAdapter(Context context, List<List<String>> stringList){
        this.context=context;
        this.stringList=stringList;
        this.stringListFiltered=stringList;
    }

    @NonNull
    @Override
    public TableDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_tabledata,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableDataAdapter.ViewHolder viewHolder, int i) {
        List<String> list=stringListFiltered.get(i);
        String name=list.get(0);
        String designation=list.get(1);
        String city=list.get(2);
        String code=list.get(3);
        String date=list.get(4);
        String salary=list.get(5);
        viewHolder.tvDataName.setText(name);
        viewHolder.tvDataSalary.setText(salary);
        viewHolder.tvDataDate.setText(date);
        viewHolder.tvDataCode.setText(code);
        viewHolder.tvDataCity.setText(city);
        viewHolder.tvDataDesignation.setText(designation);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SingleDataInfo.class);
                intent.putExtra("Name",name);
                intent.putExtra("Designation",designation);
                intent.putExtra("City",city);
                intent.putExtra("Code",code);
                intent.putExtra("Date",date);
                intent.putExtra("Salary",salary);
                context.startActivity(intent);
                ((HomeActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
    }

    @Override
    public int getItemCount() {
        return stringListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvDataName,tvDataDesignation,tvDataCity,tvDataCode,tvDataDate,tvDataSalary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDataName=itemView.findViewById(R.id.tvDataName);
            tvDataDesignation=itemView.findViewById(R.id.tvDataDesignation);
            tvDataCity=itemView.findViewById(R.id.tvDataCity);
            tvDataCode=itemView.findViewById(R.id.tvDataCode);
            tvDataDate=itemView.findViewById(R.id.tvDataDate);
            tvDataSalary=itemView.findViewById(R.id.tvDataSalary);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stringListFiltered = stringList;
                } else {
                    List<List<String>> filteredList = new ArrayList<>();
                    for (List<String> string : stringListFiltered) {

                        for (String s:string){
                            if(s.toLowerCase().contains(charString.toLowerCase())){
                                filteredList.add(string);
                                break;
                            }
                        }


                    }

                    stringListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stringListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stringListFiltered = (List<List<String>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
