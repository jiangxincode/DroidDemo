package edu.jiangxin.droiddemo.tools.mortgagecalc;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainViewPagerAdapter extends RecyclerView.Adapter<MainViewPagerAdapter.ViewHolder> {
    private final List<View> viewList;

    public MainViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout container = new FrameLayout(parent.getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FrameLayout container = (FrameLayout) holder.itemView;
        container.removeAllViews();
        View view = viewList.get(position);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        container.addView(view);
    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
