package net.sprengel;

import javax.security.auth.login.LoginException;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.sprengel.listener.CommandListener;
import net.sprengel.music.PlayerManager;

public class sprengelbot {
	
	public static sprengelbot INSTANCE;
	
	public static JDA jda;
	
	public AudioPlayerManager audioPlayerManager;
	public PlayerManager playerManager;

	public static void main(String[] args)	{
		try {
			new sprengelbot();
		} catch (LoginException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public sprengelbot() throws LoginException, IllegalArgumentException {
		
		INSTANCE = this;
		
		jda = new JDABuilder(AccountType.BOT).setToken([TOKEN]).addEventListeners(new CommandListener()).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE).build();
		
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		this.playerManager = new PlayerManager();
		
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		
	}
}
