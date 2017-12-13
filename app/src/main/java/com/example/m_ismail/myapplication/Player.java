package com.example.m_ismail.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

import java.nio.channels.Channels;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements ChannelListFragment.Callback{

    public TextView mtextView;
    public FrameLayout mframe;
    public ArrayList<ChannelDetails> channels = new ArrayList<>();
    public SimpleExoPlayer player;
    public MediaSource mediaSource;

    public Fragment fb;
    public FragmentManager fm;
    public FragmentTransaction ft;

    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);




        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                new DefaultLoadControl());


        player.setPlayWhenReady(true);


        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_player);
        player.setVideoSurfaceView(surfaceView);

        Uri uri = Uri.parse("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_sd.mpd");
        mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        //mtextView = (TextView) findViewById(R.id.text_test);
        mframe = (FrameLayout) findViewById(R.id.detail_container);


    }


    public void listView(View view) {

         fb = new ChannelListFragment();
         fm = getFragmentManager();
         ft = fm.beginTransaction().setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
         ft.replace(R.id.detail_container, fb);
         ft.commit();

    }


    @Override
    public void onItemSelected(ChannelDetails channelDetails) {

        Uri uri = Uri.parse(channelDetails.getmChannelLink());
        mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        getFragmentManager().beginTransaction().remove(fb).commit();


    }

    private MediaSource buildMediaSource(Uri uri) {

        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
        DashChunkSource.Factory dashChunkSourceFactory =
                new DefaultDashChunkSource.Factory(dataSourceFactory);

        return new DashMediaSource(uri, dataSourceFactory,
                dashChunkSourceFactory, null, null);
    }

}
