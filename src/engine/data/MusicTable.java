//package engine.data;
//
//import java.io.ByteArrayInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import engine.GameInit;
//import javazoom.jl.player.advanced.AdvancedPlayer;
//import javazoom.jl.player.advanced.PlaybackEvent;
//import javazoom.jl.player.advanced.PlaybackListener;
//
//public class MusicTable extends PackageLoader
//{
//	private final HashMap<String, byte[]> musicBuffer = new HashMap<>();
//	
//	private final ArrayList<Music> nowPlaying = new ArrayList<>();
//	
//	public MusicTable()
//	{
//		loadPackage("music");
//	}
//	
//	@Override
//	protected void populate(String name, byte[] dataBuffer) throws Exception
//	{	musicBuffer.put(name, dataBuffer);
//	}
//	
//	public class Music extends Thread
//	{
//		private final String _sndName;
//		private final AdvancedPlayer _ap;
//		
//		public Music(String sndName) throws Exception
//		{
//			_sndName = sndName;
//			byte[] musicData = musicBuffer.get(_sndName);
//			if (musicData == null)
//			{
//				GameInit.throwErrorMessage("Music Error", "Could not load sound " + _sndName);
//				throw new NullPointerException();
//			}
//			_ap = new AdvancedPlayer(new ByteArrayInputStream(musicData));
//			_ap.setPlayBackListener(new PlaybackListener()
//			{
//				@Override
//				public void playbackFinished(PlaybackEvent event) 
//				{
//					nowPlaying.remove(this);
//				}
//			});
//		}
//		
//		@Override
//		public void run()
//		{
//			try 
//			{
//				_ap.play();
//				nowPlaying.add(this);
//			}
//			catch (Exception e)
//			{
//				GameInit.throwErrorMessage("Music Error", "Failed to play music file " + _sndName);
//				e.printStackTrace();
//			}
//		}
//		
//		public void stopMusic()
//		{
//			_ap.close();
//			nowPlaying.remove(this);
//		}
//	}
//	
//	public void playSound(String sndName)
//	{
//		try
//		{
//			final Music m = new Music(sndName);
//			m.start();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	private static class InstanceHolder
//	{
//		private static final MusicTable _instance = new MusicTable();
//	}
//	
//	public static MusicTable getInstance()
//	{
//		return InstanceHolder._instance;
//	}
//}
