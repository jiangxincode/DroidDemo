package edu.jiangxin.droiddemo.opengl.opengles.matrix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.jiangxin.droiddemo.R;

/**
 * https://github.com/kenneycode/OpenGLESPro
 */
public class OpenGLMatrixActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_matrix);
        Util.context = getApplicationContext();
        RecyclerView samplesList = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        samplesList.setLayoutManager(layoutManager);
        samplesList.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<VH> {

        private final String[] sampleNames = new String[]{
                getResources().getString(R.string.sample_0),
                getResources().getString(R.string.sample_1),
                getResources().getString(R.string.sample_2),
                getResources().getString(R.string.sample_3),
                getResources().getString(R.string.sample_4),
                getResources().getString(R.string.sample_6),
                getResources().getString(R.string.sample_7),
                getResources().getString(R.string.sample_8),
                getResources().getString(R.string.sample_9)};

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sample_list_item, parent, false);
            return new VH(view);
        }

        @Override
        public int getItemCount() {
            return sampleNames.length;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            holder.button.setText(sampleNames[position]);
            holder.button.setOnClickListener((v) -> {
                Intent intent = new Intent(OpenGLMatrixActivity.this, SimpleActivity.class);
                intent.putExtra(GlobalConstants.KEY_SAMPLE_INDEX, position);
                intent.putExtra(GlobalConstants.KEY_SAMPLE_NAME, sampleNames[position]);
                startActivity(intent);
            });
        }
    }

    class VH extends RecyclerView.ViewHolder {
        Button button;
        public VH(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }

    }
}
