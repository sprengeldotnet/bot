package net.sprengel.listener;

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.ipinfo.api.IPInfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.sprengel.token;
import net.sprengel.music.PlayerManager;

public class CommandListener extends ListenerAdapter {
	
	IPInfo ipInfo = IPInfo.builder().setToken(token.IpInfoToken).build();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		
		if(event.isFromType(ChannelType.TEXT)) {
			
			String message = event.getMessage().getContentRaw().toLowerCase();
			
			String rawmsg = event.getMessage().getContentRaw();
			
			String sprengelid = "457860917099757579";
			
			PlayerManager manager = PlayerManager.getInstance();
			GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
			AudioManager audioManager = event.getGuild().getAudioManager();
			VoiceChannel voiceChannel = memberVoiceState.getChannel();
			
			TextChannel channel = event.getTextChannel();
			
			if(!event.getAuthor().isBot()) {
				if(message.startsWith("!s ")) {
					
					String[] command = message.split("!s ");
					
					if(command[1].contains("help")) {
						
						channel.sendMessage("**`Commands:`**\r" + "**!s help** - This help text.\r" + "**!s invite** - a Link to Invite this Bot.\r" +  "**!s info** - Information about this Bot.\r" +  "**!s ip** [ip] - Get info about an IP Adress.\r").queue();
						
					}
					
					else if(command[1].contains("invite")) {
						
						channel.sendMessage("> https://sprengel.net/bot/invite").queue();
						
					}
					else if(command[1].contains("source")) {
						
						channel.sendMessage("> https://sprengel.net/bot/source").queue();
						
					}
					
					else if(command[1].contains("info")) {
						channel.sendMessage("**operating system:** Debian GNU/Linux 10 (buster)\r" + "**server location:** central europe\r").queue();
					}
					
					else if(command[1].contains("ping")) {
						
						channel.sendMessage("Pong!").queue();
						channel.sendMessage("**ping:**  " + event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS) + "ms\r" + "**Gateway ping:** " + event.getJDA().getGatewayPing() + "ms").queue();
						
					}
					
					else if(command[1].contains("shutdown")) {
						
						if(event.getAuthor().getId().equals(sprengelid)) {
							
								event.getJDA().shutdown();
								
								event.getJDA().shutdownNow();
						}
						else {
							channel.sendMessage("bruh").queue();
						}
						
					}
					
					else if(command[1].startsWith("ip ")) {
						String[] string = message.split(" ");
						if(string[2].contains("ip")) {}
						else {
					         try {
					        	 
								IPResponse response = ipInfo.lookupIP(string[2]);
								
								channel.sendMessage("**ip:** " + response.getIp() + "\r**hostname:** " + response.getHostname() + "\r**city:** " + response.getCity() + "\r**region:** " + response.getRegion() + "\r**country:** " + response.getCountryName() + "\r**loc:** " + response.getLocation() + "\r**postal:** " + response.getPostal()).queue();

					         } 
					         catch (RateLimitedException ex) {
					        	System.out.println("error");
					         }
						}
					}
					
					
					
					else if(command[1].contains("join")) {
								if (audioManager.isConnected()) {
						            channel.sendMessage("I'm already connected to a channel").queue();
						            return;
						        }
								
								if (!memberVoiceState.inVoiceChannel()) {
						            channel.sendMessage("Please join a voice channel first").queue();
						            return;
						        }
								
								audioManager.openAudioConnection(voiceChannel);
						        channel.sendMessage("Joining your voice channel").queue();
						}
					
					else if(command[1].contains("quit")) {
							if(!audioManager.isConnected()) {
					            channel.sendMessage("I'm not connected to a voice channel").queue();
					            return;
					        }
							
							manager.getGuildMusicManager(event.getGuild()).player.stopTrack();
							audioManager.closeAudioConnection();
					        channel.sendMessage("Disconnected from your channel").queue();
					}
					
					else if(command[1].contains("play")) {
							
							if(command[1].startsWith("play http")) {
								
								String[] string = rawmsg.split(" ");
								String trackUrl = string[2];
								
									manager.loadAndPlay(channel, trackUrl);
								
							}
							
							else if(command[1].contains("lofi")) {
								manager.loadAndPlay(channel, "https://www.youtube.com/watch?v=5qap5aO4i9A");
							}
							
							else {
								channel.sendMessage("Please send a Youtube url to Listen to.").queue();
							}
					}
					
					else if(command[1].contains("stop")) {
					        manager.getGuildMusicManager(event.getGuild()).player.stopTrack();
					}
					
					else {
						channel.sendMessage("Sorry, I am having trouble understanding you right now.").queue();
					}
				}
				else {
					
					if(message.contentEquals("$sprengel")) {
						channel.sendMessage("messageid: " + event.getMessageId() + "\rauthor: " + event.getAuthor() + "\rchanneltype: " + event.getChannelType() + "\rchannelname: " + channel.getName() + "\rchannelid: " + channel.getId()).queue();
					}
					
					else if(message.contains("sprengel.net/secret")) {
						if(!event.getAuthor().getId().equals(sprengelid)) {
							channel.sendMessage("stop snitchin' bro.").queue();
						}
					}
					
					else if(message.contains("sprengel")) {
						String [] responses = new String[6];
						responses[0] = "hi";
						responses[1] = "hi :)";
						responses[2] = "hey";
						responses[3] = "hey :)";
						responses[4] = "hello";
						responses[5] = "hello :)";
						Random randNum = new Random();
						channel.sendMessage(responses[(randNum.nextInt(responses.length))]).queue();
					}
					
					else if(message.contains("owo")) {
						channel.sendMessage("uwu").queue();
					}
					else if(message.contains("uwu")) {
						channel.sendMessage("owo").queue();
					}
					else if(message.contains("rawr")) {
						channel.sendMessage("x3").queue();
					}
					else if(message.contains("x3")) {
						channel.sendMessage("rawr").queue();
					}
					
					else if(message.contentEquals("f")) {
						String [] responses = new String[2];
						responses[0] = "f";
						responses[1] = "F";
						Random randNum = new Random();
						channel.sendMessage(responses[(randNum.nextInt(responses.length))]).queue();
					}
					
					else if(message.contentEquals("!d bump")) {
						channel.sendMessage("https://sprengel.net/resources/hotlink-ok/bump.jpg").queue();
					}
				}
				
			}
		}
		
		else if(event.isFromType(ChannelType.PRIVATE)) {
			if(!event.getAuthor().isBot()) {
				PrivateChannel privatechannel = event.getPrivateChannel();
				
					privatechannel.sendMessage("bruh imagine sending a dm to a bot").queue();
					privatechannel.sendTyping().queueAfter(500, TimeUnit.MILLISECONDS);
					privatechannel.sendMessage("Please send a message to **`sprengel#0615`** for support or something similar.").queueAfter(3, TimeUnit.SECONDS);
			}
		}
		
	}
}
