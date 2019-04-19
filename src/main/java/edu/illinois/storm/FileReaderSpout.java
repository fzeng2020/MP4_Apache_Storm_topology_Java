package edu.illinois.storm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
public class FileReaderSpout implements IRichSpout {
    private SpoutOutputCollector collector;
    private TopologyContext context;

    private FileReader fileReader;
    private boolean isDone = false;
    
    @Override
    public void open(Map config, TopologyContext context, SpoutOutputCollector collector){

    this._context = context;
    this._collector = collector;

    try{
		this.fileReader = new FileReader(this.inputFile);
	     } catch (Exception ex){
		      throw new RuntimeException("Error reading file");
     	}

  }

    @Override
    public void nextTuple(){
        
        if(isDone){
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                // interruption is normal
            }
        }
		
		BufferedReader reader = new BufferedReader(fileReader);
        String line;
		
        try {
            while ((line = reader.readLine()) != null){
                line = line.trim();
                if(line.length() > 0){
                    collector.emit(new Values(line));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{  
            isDone = true;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("word"));
    }

    @Override
    public void close() {
        try {
			fileReader.close();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public void ack(Object msgId) {
    }

    @Override
    public void fail(Object msgId) {
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
