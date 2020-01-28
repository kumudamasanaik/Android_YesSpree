package com.yesspree.app.screens.faq.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yesspree.app.R;
import com.yesspree.app.interfaces.IAdapterClickListener;
import com.yesspree.app.modelapi.FAQRespModel;
import com.yesspree.app.modelapi.SubQuestion;
import com.yesspree.app.utility.Validation;

import java.util.List;

/**
 * Created by FuGenX-14 on 11-05-2018.
 */

public class FAQHeadersAdapter extends RecyclerView.Adapter<FAQHeadersAdapter.ViewHolder> {

    private Context context;
    private List<FAQRespModel.MainQuestion> list;
    IAdapterClickListener clickListener;
    private boolean isCollapsed = false;

    public FAQHeadersAdapter(Context context, List<FAQRespModel.MainQuestion> list, IAdapterClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.list = list;
    }

    @Override
    public FAQHeadersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_faq_headrs_list_item, parent, false);
        return new FAQHeadersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FAQHeadersAdapter.ViewHolder holder, int position) {

        if (Validation.isValidString(list.get(holder.getAdapterPosition()).getMainQuestionName())) {
            holder.tv_quetion_header.setText(list.get(holder.getAdapterPosition()).getMainQuestionName());
        }

        if (list.get(holder.getAdapterPosition()).getSubQuestionList() != null && list.get(holder.getAdapterPosition()).getSubQuestionList().size() > 0) {
            holder.subQuestionView.setVisibility(View.VISIBLE);
            holder.iv_expand_more.setVisibility(View.VISIBLE);

            holder.subQuestionView.removeAllViews();
            for (SubQuestion subQuestion : list.get(holder.getAdapterPosition()).getSubQuestionList()) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.partial_faq_sub_question_list_item, null);
                TextView SubquestionHeader = view.findViewById(R.id.tv_subQuestionHeader);
                TextView subQuestionAns = view.findViewById(R.id.tv_subQuestionAns);
                if (Validation.isValidString(subQuestion.getQuestion()))
                    SubquestionHeader.setText(subQuestion.getQuestion());

                if (Validation.isValidString(subQuestion.getAnswer()))
                    subQuestionAns.setText(subQuestion.getAnswer());
                holder.subQuestionView.addView(view);

            }


        } else {
            holder.subQuestionView.setVisibility(View.GONE);
            holder.iv_expand_more.setVisibility(View.GONE);
        }
        holder.view_questionHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(holder.getAdapterPosition()).getSubQuestionList() != null && list.get(holder.getAdapterPosition()).getSubQuestionList().size() > 0)

                {
                    if (holder.subQuestionView.getVisibility() == View.VISIBLE) {
                        holder.subQuestionView.setVisibility(View.GONE);
                        // ViewAnimationUtils.expand( holder.subQuestionView);
                        int rotateDegree = -180;
                        holder.iv_expand_more.animate().rotationBy(rotateDegree).start();
                    } else {
                        holder.subQuestionView.setVisibility(View.VISIBLE);
                        int rotateDegree = 180;
                        holder.iv_expand_more.animate().rotationBy(rotateDegree).start();
                        //ViewAnimationUtils.collapse(holder.subQuestionView);

                    }


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iv_expand_more;
        TextView tv_quetion_header;
        LinearLayout subQuestionView;
        View view_questionHeader;


        public ViewHolder(View itemView) {
            super(itemView);

            iv_expand_more = itemView.findViewById(R.id.iv_expand_more);
            tv_quetion_header = itemView.findViewById(R.id.tv_quetion_header);
            subQuestionView = itemView.findViewById(R.id.layout_sub_questionview);
            view_questionHeader = itemView.findViewById(R.id.view_questionHeader);


        }
    }

}
