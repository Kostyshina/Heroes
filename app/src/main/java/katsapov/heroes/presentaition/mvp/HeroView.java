package katsapov.heroes.presentaition.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import katsapov.heroes.R;
import katsapov.heroes.data.entitiy.Hero;

public class HeroView implements HeroContract.HeroView {


    @Override
    public void showIsLoading(Boolean isLoading, SwipeRefreshLayout swipeRefresh1) {
        swipeRefresh1.setProgressViewOffset(false, 1, 2);
    }

    @Override
    public void showHeroDetails(final Hero hero, final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.setTitle(R.string.about_person);

        TextView tvCulture = dialog.findViewById(R.id.tvCulture);
        tvCulture.setText(hero.getCulture());

        TextView tvGender = dialog.findViewById(R.id.tvGender);
        tvGender.setText(hero.getGender());

        TextView tvBorn = dialog.findViewById(R.id.tvBorn);
        tvBorn.setText(hero.getBorn());

        TextView tvDie = dialog.findViewById(R.id.tvDie);
        tvDie.setText(hero.getDie());

        TextView tvUrl = dialog.findViewById(R.id.tvUrl);
        tvUrl.setText(hero.getUrl());

        tvUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("katsapov.Browser");
                intent.setData(Uri.parse(hero.getUrl()));
                activity.startActivity(intent);
            }
        });

        TextView tvFather = dialog.findViewById(R.id.tvFather);
        tvFather.setText(hero.getFather());

        TextView tvMother = dialog.findViewById(R.id.tvMother);
        tvMother.setText(hero.getMother());

        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void showError(Activity activity, int stringError) {
        Snackbar.make(activity.findViewById(android.R.id.content), stringError, Snackbar.LENGTH_LONG).show();
    }

}
