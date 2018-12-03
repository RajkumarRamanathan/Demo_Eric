package tabbardemo.com.materialdesigntabs_demo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tabbardemo.com.materialdesigntabs_demo.R;


public abstract class ViewHolder extends RecyclerView.ViewHolder {


    public TextView name_txt, email_txt,body_txt,postId_txt,id;


    public ViewHolder(View view) {
        super(view);

        //Mapping in ViewHolder

        this.name_txt = (TextView) view.findViewById(R.id.name_txt);
        this.email_txt = (TextView) view.findViewById(R.id.email_txt);
        this.body_txt = (TextView) view.findViewById(R.id.body_txt);
        this.postId_txt = (TextView) view.findViewById(R.id.postId_txt);
        this.id = (TextView) view.findViewById(R.id.id);

    }


}