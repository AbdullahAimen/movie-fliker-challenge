package com.challenge.movie;

import android.app.Activity;
import android.content.Intent;

import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.challenge.movie.views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Simply sanity test to ensure that activity launches without any issues and shows some data.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final CountingTaskExecutorRule testRule = new CountingTaskExecutorRule();

    @Test
    public void showSomeResults() throws InterruptedException, TimeoutException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Activity activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
        testRule.drainTasks(10, TimeUnit.SECONDS);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        final RecyclerView recyclerView = activity.findViewById(R.id.main_recycler_movies);
        waitForAdapterChange(recyclerView);
        assertThat(recyclerView.getAdapter(), notNullValue());
        waitForAdapterChange(recyclerView);
        assertThat(recyclerView.getAdapter().getItemCount() > 0, is(true));
    }

    private void waitForAdapterChange(final RecyclerView recyclerView) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() ->
                recyclerView.getAdapter().registerAdapterDataObserver(
                        new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onItemRangeInserted(int positionStart, int itemCount) {
                                latch.countDown();
                            }

                            @Override
                            public void onChanged() {
                                latch.countDown();
                            }
                        }));
        if (recyclerView.getAdapter().getItemCount() > 0) {
            return;//already loaded
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), is(true));
    }
}
