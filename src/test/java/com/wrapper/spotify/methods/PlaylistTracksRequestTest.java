package com.wrapper.spotify.methods;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.TestUtil;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.Track;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import org.junit.Test;

public class PlaylistTracksRequestTest {

    @Test
    public void shouldGetTracksResult_async() throws Exception {
        String accessToken = "someToken";
        final Api api = Api.builder().accessToken(accessToken)
                .build();

        final PlaylistTracksRequest request = api
                .getPlaylistTracks("thelinmichael", "3ktAYNcRHpazJ9qecm3ptn")
                .httpManager(TestUtil.MockedHttpManager.returningJson("playlist-tracks.json"))
                .build();

        final CountDownLatch asyncCompleted = new CountDownLatch(1);

        final SettableFuture<Page<PlaylistTrack>> playlistTracksPageFuture = request.getAsync();

        Futures.addCallback(playlistTracksPageFuture, new FutureCallback<Page<PlaylistTrack>>() {

            @Override
            public void onSuccess(Page<PlaylistTrack> page) {
                assertNotNull(page);
                assertEquals(
                        "https://api.spotify.com/v1/users/thelinmichael/playlists/3ktAYNcRHpazJ9qecm3ptn/tracks",
                        page.getHref());
                assertEquals(100, page.getLimit());
                assertNull(page.getNext());
                assertEquals(0, page.getOffset());
                assertNull(page.getPrevious());
                assertTrue(page.getTotal() > 0);

                final PlaylistTrack playlistTrack = page.getItems().get(0);
                assertNotNull(playlistTrack.getAddedAt());
                assertNotNull(playlistTrack.getAddedBy());

                final Track track = playlistTrack.getTrack();
                assertTrue(track.getPopularity() >= 0);

                asyncCompleted.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                fail("Failed to resolve future");
            }
        });

        asyncCompleted.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void shouldGetStarredResult_async() throws Exception {
        String accessToken = "someToken";
        final Api api = Api.builder().accessToken(accessToken)
                .build();

        final PlaylistTracksRequest request = api
                .getStarred("thelinmichael")
                .httpManager(TestUtil.MockedHttpManager.returningJson("starred-tracks.json"))
                .build();

        final CountDownLatch asyncCompleted = new CountDownLatch(1);

        final SettableFuture<Page<PlaylistTrack>> playlistTracksPageFuture = request.getAsync();

        Futures.addCallback(playlistTracksPageFuture, new FutureCallback<Page<PlaylistTrack>>() {

            @Override
            public void onSuccess(Page<PlaylistTrack> page) {
                assertNotNull(page);
                assertEquals(
                        "https://api.spotify.com/v1/users/thelinmichael/starred/tracks?offset=0&limit=100",
                        page.getHref());
                assertEquals(100, page.getLimit());
                assertNull(page.getNext());
                assertEquals(0, page.getOffset());
                assertNull(page.getPrevious());
                assertTrue(page.getTotal() > 0);

                final PlaylistTrack playlistTrack = page.getItems().get(0);
                assertNotNull(playlistTrack.getAddedAt());
                assertNotNull(playlistTrack.getAddedBy());

                final Track track = playlistTrack.getTrack();
                assertTrue(track.getPopularity() >= 0);

                asyncCompleted.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                fail("Failed to resolve future");
            }
        });

        asyncCompleted.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void shouldGetTracksResult_sync() throws Exception {
        String accessToken = "someToken";
        final Api api = Api.builder().accessToken(accessToken).build();

        final PlaylistTracksRequest request = api
                .getPlaylistTracks("thelinmichael", "3ktAYNcRHpazJ9qecm3ptn")
                .httpManager(TestUtil.MockedHttpManager.returningJson("playlist-tracks.json"))
                .build();

        final Page<PlaylistTrack> page = request.get();

        assertNotNull(page);
        assertEquals(
                "https://api.spotify.com/v1/users/thelinmichael/playlists/3ktAYNcRHpazJ9qecm3ptn/tracks",
                page.getHref());
        assertEquals(100, page.getLimit());
        assertNull(page.getNext());
        assertEquals(0, page.getOffset());
        assertNull(page.getPrevious());
        assertTrue(page.getTotal() > 0);

        final PlaylistTrack playlistTrack = page.getItems().get(0);
        assertNotNull(playlistTrack.getAddedAt());
        assertNotNull(playlistTrack.getAddedBy());

        final Track track = playlistTrack.getTrack();
        assertTrue(track.getPopularity() >= 0);
    }

    @Test
    public void shouldGetStarredResult_sync() throws Exception {
        String accessToken = "someToken";
        final Api api = Api.builder().accessToken(accessToken).build();

        final PlaylistTracksRequest request = api.getStarred("thelinmichael")
                .httpManager(TestUtil.MockedHttpManager.returningJson("starred-tracks.json"))
                .build();

        final Page<PlaylistTrack> page = request.get();

        assertNotNull(page);
        assertEquals(
                "https://api.spotify.com/v1/users/thelinmichael/starred/tracks?offset=0&limit=100",
                page.getHref());
        assertEquals(100, page.getLimit());
        assertNull(page.getNext());
        assertEquals(0, page.getOffset());
        assertNull(page.getPrevious());
        assertTrue(page.getTotal() > 0);

        final PlaylistTrack playlistTrack = page.getItems().get(0);
        assertNotNull(playlistTrack.getAddedAt());
        assertNotNull(playlistTrack.getAddedBy());

        final Track track = playlistTrack.getTrack();
        assertTrue(track.getPopularity() >= 0);
    }

    @Test
    public void shouldGetReducedPlaylistWithFields() throws Exception {
        String accessToken = "someToken";
        final Api api = Api.builder().accessToken(accessToken).build();

        final PlaylistTracksRequest request = api.getPlaylistTracks("musicidgant", "7j4fCrXYkEp6XQZQwUm31G")
                .httpManager(TestUtil.MockedHttpManager.returningJson("playlist-tracks-with-fields-response.json"))
                .fields("limit,next,offset,previous,total,items(track.id,track.album.name,track.artists.name,track.name,track.external_ids.isrc,track.disc_number,track.track_number,track.duration_ms)")
                .build();

        final Page<PlaylistTrack> playlistTracks = request.get();

        assertNotNull(playlistTracks.getTotal());
        assertNotNull(playlistTracks.getLimit());
        assertNotNull(playlistTracks.getOffset());
        assertNull(playlistTracks.getNext());
        assertNull(playlistTracks.getPrevious());

        assertNotNull(playlistTracks.getItems());
        assertFalse(playlistTracks.getItems().isEmpty());
        assertEquals(3, playlistTracks.getItems().size());

        PlaylistTrack track1 = playlistTracks.getItems().get(0);
        PlaylistTrack track2 = playlistTracks.getItems().get(1);
        PlaylistTrack track3 = playlistTracks.getItems().get(2);

        assertNotNull(track1);
        assertNotNull(track2);
        assertNotNull(track3);
        
        assertEquals("3sOhpTLU7c6we1RcZJtzO6", track1.getTrack().getId());
        assertEquals("76dv5KLRIAejZNmYqYnFYw", track2.getTrack().getId());
        assertEquals("2yXfkGdRTSPgSFJfOGsLhk", track3.getTrack().getId());
        
        assertNotNull(track1.getTrack().getAlbum());
        assertNotNull(track1.getTrack().getArtists());
        
        assertNotNull(track2.getTrack().getAlbum());
        assertNotNull(track2.getTrack().getArtists());

        assertNotNull(track3.getTrack().getAlbum());
        assertNotNull(track3.getTrack().getArtists());
    }
}
