package com.carlca.pluginmanager;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class PluginManagerDemoData {

	public static List<Triplet<String, String, String>> getDemoData() {
		List<Triplet<String, String, String>> demoData = new ArrayList<>();
		demoData.add(buildTriplet("audio,audio.vital.synth,Vital.clap"));
		demoData.add(buildTriplet("audiodamage,com.audiodamage.dubstation2,Dubstation 2.clap"));
		demoData.add(buildTriplet("audiomodern,com.audiomodern.chordjam,Chordjam.clap"));
		demoData.add(buildTriplet("audiority,com.audiority.polaris,Audiority/Polaris.clap"));
		demoData.add(buildTriplet("audiority,com.audiority.spacestationum282,Audiority/Space Station UM282.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.blindfoldeq,BlindfoldEQ.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.filterjam,Filterjam.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.fogconvolver2,FogConvolver2.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.noises,Noises.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.outerspace,OuterSpace.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.spacestrip,SpaceStrip.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.springs,Springs.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.thingsbubbles,ThingsBubbles.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.thingscrusher,ThingsCrusher.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.thingsflipeq,ThingsFlipEQ.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.thingsmotor,ThingsMotor.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.thingstexture,ThingsTexture.clap"));
		demoData.add(buildTriplet("audiothing,com.audiothing.valves,Valves.clap"));
		demoData.add(buildTriplet("birdbird,com.birdbird.birdsampler,Rolling Sampler.clap"));
		demoData.add(buildTriplet("chowdsp,org.chowdsp.byod,BYOD.clap"));
		demoData.add(buildTriplet("chowdsp,org.chowdsp.chowtapemodel,CHOWTapeModel.clap"));
		demoData.add(buildTriplet("chowdsp,org.chowdsp.chowkick,ChowKick.clap"));
		demoData.add(buildTriplet("chowdsp,org.chowdsp.chowmultitool,ChowMultiTool.clap"));
		demoData.add(buildTriplet("cwitec,com.cwitec.tx16wx,TX16Wx.clap"));
		demoData.add(buildTriplet("josephlyncheski,com.josephlyncheski.minimetersserver,MiniMetersServer.clap"));
		demoData.add(buildTriplet("nakst,nakst.apricot,Apricot.clap"));
		demoData.add(buildTriplet("nakst,nakst.fluctus,Fluctus.clap"));
		demoData.add(buildTriplet("nakst,nakst.regency,Regency.clap"));
		demoData.add(buildTriplet("sonosaurus,com.sonosaurus.paulxstretch,PaulXStretch.clap"));
		demoData.add(buildTriplet("studio,studio.kx.distrho.aida-x,AIDA-X.clap"));
		demoData.add(buildTriplet("studio,studio.kx.distrho.wstd_dl3y,WSTD_DL3Y.clap"));
		demoData.add(buildTriplet("studio,studio.kx.distrho.wstd_fl3ngr,WSTD_FL3NGR.clap"));
		demoData.add(buildTriplet("surge-synth-team,org.surge-synth-team.surge-xt-fx,Surge XT Effects.clap"));
		demoData.add(buildTriplet("surge-synth-team,org.surge-synth-team.surge-xt,Surge XT.clap"));
		demoData.add(buildTriplet("the wave warden,,Odin2.clap"));
		demoData.add(buildTriplet("theusualsuspects,com.theusualsuspects.tusv,Osirus.clap"));
		demoData.add(buildTriplet("theusualsuspects,com.theusualsuspects.tmqs,Vavra.clap"));
		demoData.add(buildTriplet("timothyschoen,com.timothyschoen.plugdata-fx,plugdata-fx.clap"));
		demoData.add(buildTriplet("timothyschoen,com.timothyschoen.plugdata,plugdata.clap"));
		demoData.add(buildTriplet("toguaudioline,ch.toguaudioline.talnoisemaker,TAL-NoiseMaker.clap"));
		demoData.add(buildTriplet("vcvrack,com.vcvrack.rack,VCV Rack 2.clap"));
		return demoData;		
	}

	private static Triplet<String, String, String> buildTriplet(String csvLine) {
		String[] parts = csvLine.split(",");
		if (parts.length == 3) {
			return new Triplet<>(parts[0], parts[1], parts[2]);
		}
		return new Triplet<>("invalid","invalid","invalid");
	}

}

/*

*/