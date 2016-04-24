package API;
      /*
DEVELOPING GAME IN JAVA

Caracteristiques

Editeur : NEW RIDERS
Auteur : BRACKEEN
Parution : 09 2003
Pages : 972
Isbn : 1-59273-005-1
Reliure : Paperback
Disponibilite : Disponible a la librairie
*/


import java.awt.Color;
import java.awt.Container;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.RepaintManager;
/* SoundManager code from:
*  http://www.java2s.com/Code/Java/Development-Class/SoundManagerTest.htm
*/

class SoundManager extends ThreadPool {

 private AudioFormat playbackFormat;

 private ThreadLocal localLine;

 private ThreadLocal localBuffer;

 private Object pausedLock;

 private boolean paused;

 /**
  * Creates a new SoundManager using the maximum number of simultaneous
  * sounds.
  */
 public SoundManager(AudioFormat playbackFormat) {
   this(playbackFormat, getMaxSimultaneousSounds(playbackFormat));
 }

 /**
  * Creates a new SoundManager with the specified maximum number of
  * simultaneous sounds.
  */
 public SoundManager(AudioFormat playbackFormat, int maxSimultaneousSounds) {
   super(Math.min(maxSimultaneousSounds,
       getMaxSimultaneousSounds(playbackFormat)));
   this.playbackFormat = playbackFormat;
   localLine = new ThreadLocal();
   localBuffer = new ThreadLocal();
   pausedLock = new Object();
   // notify threads in pool it's ok to start
   synchronized (this) {
     notifyAll();
   }
 }

 /**
  * Gets the maximum number of simultaneous sounds with the specified
  * AudioFormat that the default mixer can play.
  */
 public static int getMaxSimultaneousSounds(AudioFormat playbackFormat) {
   DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class,
       playbackFormat);
   Mixer mixer = AudioSystem.getMixer(null);
   return mixer.getMaxLines(lineInfo);
 }

 /**
  * Does any clean up before closing.
  */
 protected void cleanUp() {
   // signal to unpause
   setPaused(false);

   // close the mixer (stops any running sounds)
   Mixer mixer = AudioSystem.getMixer(null);
   if (mixer.isOpen()) {
     mixer.close();
   }
 }

 public void close() {
   cleanUp();
   super.close();
 }

 public void join() {
   cleanUp();
   super.join();
 }

 /**
  * Sets the paused state. Sounds may not pause immediately.
  */
 public void setPaused(boolean paused) {
   if (this.paused != paused) {
     synchronized (pausedLock) {
       this.paused = paused;
       if (!paused) {
         // restart sounds
         pausedLock.notifyAll();
       }
     }
   }
 }

 /**
  * Returns the paused state.
  */
 public boolean isPaused() {
   return paused;
 }

 /**
  * Loads a Sound from the file system. Returns null if an error occurs.
  */
 public Sound getSound(String filename) {
   return getSound(getAudioInputStream(filename));
 }

 /**
  * Loads a Sound from an input stream. Returns null if an error occurs.
  */
 public Sound getSound(InputStream is) {
   return getSound(getAudioInputStream(is));
 }

 /**
  * Loads a Sound from an AudioInputStream.
  */
 public Sound getSound(AudioInputStream audioStream) {
   if (audioStream == null) {
     return null;
   }

   // get the number of bytes to read
   int length = (int) (audioStream.getFrameLength() * audioStream
       .getFormat().getFrameSize());

   // read the entire stream
   byte[] samples = new byte[length];
   DataInputStream is = new DataInputStream(audioStream);
   try {
     is.readFully(samples);
     is.close();
   } catch (IOException ex) {
     ex.printStackTrace();
   }

   // return the samples
   return new Sound(samples);
 }

 /**
  * Creates an AudioInputStream from a sound from the file system.
  */
 public AudioInputStream getAudioInputStream(String filename) {
   try {
     return getAudioInputStream(new FileInputStream(filename));
   } catch (IOException ex) {
     ex.printStackTrace();
     return null;
   }
 }

 /**
  * Creates an AudioInputStream from a sound from an input stream
  */
 public AudioInputStream getAudioInputStream(InputStream is) {

   try {
     if (!is.markSupported()) {
       is = new BufferedInputStream(is);
     }
     // open the source stream
     AudioInputStream source = AudioSystem.getAudioInputStream(is);

     // convert to playback format
     return AudioSystem.getAudioInputStream(playbackFormat, source);
   } catch (UnsupportedAudioFileException ex) {
     ex.printStackTrace();
   } catch (IOException ex) {
     ex.printStackTrace();
   } catch (IllegalArgumentException ex) {
     ex.printStackTrace();
   }

   return null;
 }

 /**
  * Plays a sound. This method returns immediately.
  */
 public InputStream play(Sound sound) {
   return play(sound, null, false);
 }

 /**
  * Plays a sound with an optional SoundFilter, and optionally looping. This
  * method returns immediately.
  */
 public InputStream play(Sound sound, SoundFilter filter, boolean loop) {
   InputStream is;
   if (sound != null) {
     if (loop) {
       is = new LoopingByteInputStream(sound.getSamples());
     } else {
       is = new ByteArrayInputStream(sound.getSamples());
     }

     return play(is, filter);
   }
   return null;
 }

 /**
  * Plays a sound from an InputStream. This method returns immediately.
  */
 public InputStream play(InputStream is) {
   return play(is, null);
 }

 /**
  * Plays a sound from an InputStream with an optional sound filter. This
  * method returns immediately.
  */
 public InputStream play(InputStream is, SoundFilter filter) {
   if (is != null) {
     if (filter != null) {
       is = new FilteredSoundStream(is, filter);
     }
     runTask(new SoundPlayer(is));
   }
   return is;
 }

 /**
  * Signals that a PooledThread has started. Creates the Thread's line and
  * buffer.
  */
 protected void threadStarted() {
   // wait for the SoundManager constructor to finish
   synchronized (this) {
     try {
       wait();
     } catch (InterruptedException ex) {
     }
   }

   // use a short, 100ms (1/10th sec) buffer for filters that
   // change in real-time
   int bufferSize = playbackFormat.getFrameSize()
       * Math.round(playbackFormat.getSampleRate() / 10);

   // create, open, and start the line
   SourceDataLine line;
   DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class,
       playbackFormat);
   try {
     line = (SourceDataLine) AudioSystem.getLine(lineInfo);
     line.open(playbackFormat, bufferSize);
   } catch (LineUnavailableException ex) {
     // the line is unavailable - signal to end this thread
     Thread.currentThread().interrupt();
     return;
   }

   line.start();

   // create the buffer
   byte[] buffer = new byte[bufferSize];

   // set this thread's locals
   localLine.set(line);
   localBuffer.set(buffer);
 }

 /**
  * Signals that a PooledThread has stopped. Drains and closes the Thread's
  * Line.
  */
 protected void threadStopped() {
   SourceDataLine line = (SourceDataLine) localLine.get();
   if (line != null) {
     line.drain();
     line.close();
   }
 }

 /**
  * The SoundPlayer class is a task for the PooledThreads to run. It receives
  * the threads's Line and byte buffer from the ThreadLocal variables and
  * plays a sound from an InputStream.
  * <p>
  * This class only works when called from a PooledThread.
  */
 protected class SoundPlayer implements Runnable {

   private InputStream source;

   public SoundPlayer(InputStream source) {
     this.source = source;
   }

   public void run() {
     // get line and buffer from ThreadLocals
     SourceDataLine line = (SourceDataLine) localLine.get();
     byte[] buffer = (byte[]) localBuffer.get();
     if (line == null || buffer == null) {
       // the line is unavailable
       return;
     }

     // copy data to the line
     try {
       int numBytesRead = 0;
       while (numBytesRead != -1) {
         // if paused, wait until unpaused
         synchronized (pausedLock) {
           if (paused) {
             try {
               pausedLock.wait();
             } catch (InterruptedException ex) {
               return;
             }
           }
         }
         // copy data
         numBytesRead = source.read(buffer, 0, buffer.length);
         if (numBytesRead != -1) {
           line.write(buffer, 0, numBytesRead);
         }
       }
     } catch (IOException ex) {
       ex.printStackTrace();
     }

   }
 }

}

class MidiPlayer implements MetaEventListener {

 // Midi meta event
 public static final int END_OF_TRACK_MESSAGE = 47;

 private Sequencer sequencer;

 private boolean loop;

 private boolean paused;

 /**
  * Creates a new MidiPlayer object.
  */
 public MidiPlayer() {
   try {
     sequencer = MidiSystem.getSequencer();
     sequencer.open();
     sequencer.addMetaEventListener(this);
   } catch (MidiUnavailableException ex) {
     sequencer = null;
   }
 }

 /**
  * Loads a sequence from the file system. Returns null if an error occurs.
  */
 public Sequence getSequence(String filename) {
   try {
     return getSequence(new FileInputStream(filename));
   } catch (IOException ex) {
     ex.printStackTrace();
     return null;
   }
 }

 /**
  * Loads a sequence from an input stream. Returns null if an error occurs.
  */
 public Sequence getSequence(InputStream is) {
   try {
     if (!is.markSupported()) {
       is = new BufferedInputStream(is);
     }
     Sequence s = MidiSystem.getSequence(is);
     is.close();
     return s;
   } catch (InvalidMidiDataException ex) {
     ex.printStackTrace();
     return null;
   } catch (IOException ex) {
     ex.printStackTrace();
     return null;
   }
 }

 /**
  * Plays a sequence, optionally looping. This method returns immediately.
  * The sequence is not played if it is invalid.
  */
 public void play(Sequence sequence, boolean loop) {
   if (sequencer != null && sequence != null && sequencer.isOpen()) {
     try {
       sequencer.setSequence(sequence);
       sequencer.start();
       this.loop = loop;
     } catch (InvalidMidiDataException ex) {
       ex.printStackTrace();
     }
   }
 }

 /**
  * This method is called by the sound system when a meta event occurs. In
  * this case, when the end-of-track meta event is received, the sequence is
  * restarted if looping is on.
  */
 public void meta(MetaMessage event) {
   if (event.getType() == END_OF_TRACK_MESSAGE) {
     if (sequencer != null && sequencer.isOpen() && loop) {
       sequencer.start();
     }
   }
 }

 /**
  * Stops the sequencer and resets its position to 0.
  */
 public void stop() {
   if (sequencer != null && sequencer.isOpen()) {
     sequencer.stop();
     sequencer.setMicrosecondPosition(0);
   }
 }

 /**
  * Closes the sequencer.
  */
 public void close() {
   if (sequencer != null && sequencer.isOpen()) {
     sequencer.close();
   }
 }

 /**
  * Gets the sequencer.
  */
 public Sequencer getSequencer() {
   return sequencer;
 }

 /**
  * Sets the paused state. Music may not immediately pause.
  */
 public void setPaused(boolean paused) {
   if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
     this.paused = paused;
     if (paused) {
       sequencer.stop();
     } else {
       sequencer.start();
     }
   }
 }

 /**
  * Returns the paused state.
  */
 public boolean isPaused() {
   return paused;
 }

}

/**
* Simple abstract class used for testing. Subclasses should implement the
* draw() method.
*/

abstract class GameCore {

 protected static final int FONT_SIZE = 24;

 private static final DisplayMode POSSIBLE_MODES[] = {
     new DisplayMode(800, 600, 32, 0), new DisplayMode(800, 600, 24, 0),
     new DisplayMode(800, 600, 16, 0), new DisplayMode(640, 480, 32, 0),
     new DisplayMode(640, 480, 24, 0), new DisplayMode(640, 480, 16, 0) };

 private boolean isRunning;

 protected ScreenManager screen;

 /**
  * Signals the game loop that it's time to quit
  */
 public void stop() {
   isRunning = false;
 }

 /**
  * Calls init() and gameLoop()
  */
 public void run() {
   try {
     init();
     gameLoop();
   } finally {
     screen.restoreScreen();
   }
 }

 /**
  * Sets full screen mode and initiates and objects.
  */
 public void init() {
   screen = new ScreenManager();
   DisplayMode displayMode = screen
       .findFirstCompatibleMode(POSSIBLE_MODES);
   screen.setFullScreen(displayMode);

   Window window = screen.getFullScreenWindow();
   window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
   window.setBackground(Color.blue);
   window.setForeground(Color.white);

   isRunning = true;
 }

 public Image loadImage(String fileName) {
   return new ImageIcon(fileName).getImage();
 }

 /**
  * Runs through the game loop until stop() is called.
  */
 public void gameLoop() {
   long startTime = System.currentTimeMillis();
   long currTime = startTime;

   while (isRunning) {
     long elapsedTime = System.currentTimeMillis() - currTime;
     currTime += elapsedTime;

     // update
     update(elapsedTime);

     // draw the screen
     Graphics2D g = screen.getGraphics();
     draw(g);
     g.dispose();
     screen.update();

     // take a nap
     try {
       Thread.sleep(20);
     } catch (InterruptedException ex) {
     }
   }
 }

 /**
  * Updates the state of the game/animation based on the amount of elapsed
  * time that has passed.
  */
 public void update(long elapsedTime) {
   // do nothing
 }

 /**
  * Draws to the screen. Subclasses must override this method.
  */
 public abstract void draw(Graphics2D g);
}

/**
* The NullRepaintManager is a RepaintManager that doesn't do any repainting.
* Useful when all the rendering is done manually by the application.
*/

class NullRepaintManager extends RepaintManager {

 /**
  * Installs the NullRepaintManager.
  */
 public static void install() {
   RepaintManager repaintManager = new NullRepaintManager();
   repaintManager.setDoubleBufferingEnabled(false);
   RepaintManager.setCurrentManager(repaintManager);
 }

 public void addInvalidComponent(JComponent c) {
   // do nothing
 }

 public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
   // do nothing
 }

 public void markCompletelyDirty(JComponent c) {
   // do nothing
 }

 public void paintDirtyRegions() {
   // do nothing
 }

}

/**
* The ScreenManager class manages initializing and displaying full screen
* graphics modes.
*/

class ScreenManager {

 private GraphicsDevice device;

 /**
  * Creates a new ScreenManager object.
  */
 public ScreenManager() {
   GraphicsEnvironment environment = GraphicsEnvironment
       .getLocalGraphicsEnvironment();
   device = environment.getDefaultScreenDevice();
 }

 /**
  * Returns a list of compatible display modes for the default device on the
  * system.
  */
 public DisplayMode[] getCompatibleDisplayModes() {
   return device.getDisplayModes();
 }

 /**
  * Returns the first compatible mode in a list of modes. Returns null if no
  * modes are compatible.
  */
 public DisplayMode findFirstCompatibleMode(DisplayMode modes[]) {
   DisplayMode goodModes[] = device.getDisplayModes();
   for (int i = 0; i < modes.length; i++) {
     for (int j = 0; j < goodModes.length; j++) {
       if (displayModesMatch(modes[i], goodModes[j])) {
         return modes[i];
       }
     }

   }

   return null;
 }

 /**
  * Returns the current display mode.
  */
 public DisplayMode getCurrentDisplayMode() {
   return device.getDisplayMode();
 }

 /**
  * Determines if two display modes "match". Two display modes match if they
  * have the same resolution, bit depth, and refresh rate. The bit depth is
  * ignored if one of the modes has a bit depth of
  * DisplayMode.BIT_DEPTH_MULTI. Likewise, the refresh rate is ignored if one
  * of the modes has a refresh rate of DisplayMode.REFRESH_RATE_UNKNOWN.
  */
 public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2)

 {
   if (mode1.getWidth() != mode2.getWidth()
       || mode1.getHeight() != mode2.getHeight()) {
     return false;
   }

   if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
       && mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
       && mode1.getBitDepth() != mode2.getBitDepth()) {
     return false;
   }

   if (mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
       && mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
       && mode1.getRefreshRate() != mode2.getRefreshRate()) {
     return false;
   }

   return true;
 }

 /**
  * Enters full screen mode and changes the display mode. If the specified
  * display mode is null or not compatible with this device, or if the
  * display mode cannot be changed on this system, the current display mode
  * is used.
  * <p>
  * The display uses a BufferStrategy with 2 buffers.
  */
 public void setFullScreen(DisplayMode displayMode) {
   final JFrame frame = new JFrame();
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setUndecorated(true);
   frame.setIgnoreRepaint(true);
   frame.setResizable(false);

   device.setFullScreenWindow(frame);

   if (displayMode != null && device.isDisplayChangeSupported()) {
     try {
       device.setDisplayMode(displayMode);
     } catch (IllegalArgumentException ex) {
     }
     // fix for mac os x
     frame.setSize(displayMode.getWidth(), displayMode.getHeight());
   }
   // avoid potential deadlock in 1.4.1_02
   try {
     EventQueue.invokeAndWait(new Runnable() {
       public void run() {
         frame.createBufferStrategy(2);
       }
     });
   } catch (InterruptedException ex) {
     // ignore
   } catch (InvocationTargetException ex) {
     // ignore
   }

 }

 /**
  * Gets the graphics context for the display. The ScreenManager uses double
  * buffering, so applications must call update() to show any graphics drawn.
  * <p>
  * The application must dispose of the graphics object.
  */
 public Graphics2D getGraphics() {
   Window window = device.getFullScreenWindow();
   if (window != null) {
     BufferStrategy strategy = window.getBufferStrategy();
     return (Graphics2D) strategy.getDrawGraphics();
   } else {
     return null;
   }
 }

 /**
  * Updates the display.
  */
 public void update() {
   Window window = device.getFullScreenWindow();
   if (window != null) {
     BufferStrategy strategy = window.getBufferStrategy();
     if (!strategy.contentsLost()) {
       strategy.show();
     }
   }
   // Sync the display on some systems.
   // (on Linux, this fixes event queue problems)
   Toolkit.getDefaultToolkit().sync();
 }

 /**
  * Returns the window currently used in full screen mode. Returns null if
  * the device is not in full screen mode.
  */
 public JFrame getFullScreenWindow() {
   return (JFrame) device.getFullScreenWindow();
 }

 /**
  * Returns the width of the window currently used in full screen mode.
  * Returns 0 if the device is not in full screen mode.
  */
 public int getWidth() {
   Window window = device.getFullScreenWindow();
   if (window != null) {
     return window.getWidth();
   } else {
     return 0;
   }
 }

 /**
  * Returns the height of the window currently used in full screen mode.
  * Returns 0 if the device is not in full screen mode.
  */
 public int getHeight() {
   Window window = device.getFullScreenWindow();
   if (window != null) {
     return window.getHeight();
   } else {
     return 0;
   }
 }

 /**
  * Restores the screen's display mode.
  */
 public void restoreScreen() {
   Window window = device.getFullScreenWindow();
   if (window != null) {
     window.dispose();
   }
   device.setFullScreenWindow(null);
 }

 /**
  * Creates an image compatible with the current display.
  */
 public BufferedImage createCompatibleImage(int w, int h, int transparancy) {
   Window window = device.getFullScreenWindow();
   if (window != null) {
     GraphicsConfiguration gc = window.getGraphicsConfiguration();
     return gc.createCompatibleImage(w, h, transparancy);
   }
   return null;
 }
}

/**
* The EchoFilter class is a SoundFilter that emulates an echo.
*
* @see FilteredSoundStream
*/

class EchoFilter extends SoundFilter {

 private short[] delayBuffer;

 private int delayBufferPos;

 private float decay;

 /**
  * Creates an EchoFilter with the specified number of delay samples and the
  * specified decay rate.
  * <p>
  * The number of delay samples specifies how long before the echo is
  * initially heard. For a 1 second echo with mono, 44100Hz sound, use 44100
  * delay samples.
  * <p>
  * The decay value is how much the echo has decayed from the source. A decay
  * value of .5 means the echo heard is half as loud as the source.
  */
 public EchoFilter(int numDelaySamples, float decay) {
   delayBuffer = new short[numDelaySamples];
   this.decay = decay;
 }

 /**
  * Gets the remaining size, in bytes, of samples that this filter can echo
  * after the sound is done playing. Ensures that the sound will have decayed
  * to below 1% of maximum volume (amplitude).
  */
 public int getRemainingSize() {
   float finalDecay = 0.01f;
   // derived from Math.pow(decay,x) <= finalDecay
   int numRemainingBuffers = (int) Math.ceil(Math.log(finalDecay)
       / Math.log(decay));
   int bufferSize = delayBuffer.length * 2;

   return bufferSize * numRemainingBuffers;
 }

 /**
  * Clears this EchoFilter's internal delay buffer.
  */
 public void reset() {
   for (int i = 0; i < delayBuffer.length; i++) {
     delayBuffer[i] = 0;
   }
   delayBufferPos = 0;
 }

 /**
  * Filters the sound samples to add an echo. The samples played are added to
  * the sound in the delay buffer multipied by the decay rate. The result is
  * then stored in the delay buffer, so multiple echoes are heard.
  */
 public void filter(byte[] samples, int offset, int length) {

   for (int i = offset; i < offset + length; i += 2) {
     // update the sample
     short oldSample = getSample(samples, i);
     short newSample = (short) (oldSample + decay
         * delayBuffer[delayBufferPos]);
     setSample(samples, i, newSample);

     // update the delay buffer
     delayBuffer[delayBufferPos] = newSample;
     delayBufferPos++;
     if (delayBufferPos == delayBuffer.length) {
       delayBufferPos = 0;
     }
   }
 }

}

/**
* A abstract class designed to filter sound samples. Since SoundFilters may use
* internal buffering of samples, a new SoundFilter object should be created for
* every sound played. However, SoundFilters can be reused after they are
* finished by called the reset() method.
* <p>
* Assumes all samples are 16-bit, signed, little-endian format.
*
* @see FilteredSoundStream
*/

abstract class SoundFilter {

 /**
  * Resets this SoundFilter. Does nothing by default.
  */
 public void reset() {
   // do nothing
 }

 /**
  * Gets the remaining size, in bytes, that this filter plays after the sound
  * is finished. An example would be an echo that plays longer than it's
  * original sound. This method returns 0 by default.
  */
 public int getRemainingSize() {
   return 0;
 }

 /**
  * Filters an array of samples. Samples should be in 16-bit, signed,
  * little-endian format.
  */
 public void filter(byte[] samples) {
   filter(samples, 0, samples.length);
 }

 /**
  * Filters an array of samples. Samples should be in 16-bit, signed,
  * little-endian format. This method should be implemented by subclasses.
  */
 public abstract void filter(byte[] samples, int offset, int length);

 /**
  * Convenience method for getting a 16-bit sample from a byte array. Samples
  * should be in 16-bit, signed, little-endian format.
  */
 public static short getSample(byte[] buffer, int position) {
   return (short) (((buffer[position + 1] & 0xff) << 8) | (buffer[position] & 0xff));
 }

 /**
  * Convenience method for setting a 16-bit sample in a byte array. Samples
  * should be in 16-bit, signed, little-endian format.
  */
 public static void setSample(byte[] buffer, int position, short sample) {
   buffer[position] = (byte) (sample & 0xff);
   buffer[position + 1] = (byte) ((sample >> 8) & 0xff);
 }

}

/**
* A thread pool is a group of a limited number of threads that are used to
* execute tasks.
*/

class ThreadPool extends ThreadGroup {

 private boolean isAlive;

 private LinkedList taskQueue;

 private int threadID;

 private static int threadPoolID;

 /**
  * Creates a new ThreadPool.
  *
  * @param numThreads
  *            The number of threads in the pool.
  */
 public ThreadPool(int numThreads) {
   super("ThreadPool-" + (threadPoolID++));
   setDaemon(true);

   isAlive = true;

   taskQueue = new LinkedList();
   for (int i = 0; i < numThreads; i++) {
     new PooledThread().start();
   }
 }

 /**
  * Requests a new task to run. This method returns immediately, and the task
  * executes on the next available idle thread in this ThreadPool.
  * <p>
  * Tasks start execution in the order they are received.
  *
  * @param task
  *            The task to run. If null, no action is taken.
  * @throws IllegalStateException
  *             if this ThreadPool is already closed.
  */
 public synchronized void runTask(Runnable task) {
   if (!isAlive) {
     throw new IllegalStateException();
   }
   if (task != null) {
     taskQueue.add(task);
     notify();
   }

 }

 protected synchronized Runnable getTask() throws InterruptedException {
   while (taskQueue.size() == 0) {
     if (!isAlive) {
       return null;
     }
     wait();
   }
   return (Runnable) taskQueue.removeFirst();
 }

 /**
  * Closes this ThreadPool and returns immediately. All threads are stopped,
  * and any waiting tasks are not executed. Once a ThreadPool is closed, no
  * more tasks can be run on this ThreadPool.
  */
 public synchronized void close() {
   if (isAlive) {
     isAlive = false;
     taskQueue.clear();
     interrupt();
   }
 }

 /**
  * Closes this ThreadPool and waits for all running threads to finish. Any
  * waiting tasks are executed.
  */
 public void join() {
   // notify all waiting threads that this ThreadPool is no
   // longer alive
   synchronized (this) {
     isAlive = false;
     notifyAll();
   }

   // wait for all threads to finish
   Thread[] threads = new Thread[activeCount()];
   int count = enumerate(threads);
   for (int i = 0; i < count; i++) {
     try {
       threads[i].join();
     } catch (InterruptedException ex) {
     }
   }
 }

 /**
  * Signals that a PooledThread has started. This method does nothing by
  * default; subclasses should override to do any thread-specific startup
  * tasks.
  */
 protected void threadStarted() {
   // do nothing
 }

 /**
  * Signals that a PooledThread has stopped. This method does nothing by
  * default; subclasses should override to do any thread-specific cleanup
  * tasks.
  */
 protected void threadStopped() {
   // do nothing
 }

 /**
  * A PooledThread is a Thread in a ThreadPool group, designed to run tasks
  * (Runnables).
  */
 private class PooledThread extends Thread {

   public PooledThread() {
     super(ThreadPool.this, "PooledThread-" + (threadID++));
   }

   public void run() {
     // signal that this thread has started
     threadStarted();

     while (!isInterrupted()) {

       // get a task to run
       Runnable task = null;
       try {
         task = getTask();
       } catch (InterruptedException ex) {
       }

       // if getTask() returned null or was interrupted,
       // close this thread.
       if (task == null) {
         break;
       }

       // run the task, and eat any exceptions it throws
       try {
         task.run();
       } catch (Throwable t) {
         uncaughtException(this, t);
       }
     }
     // signal that this thread has stopped
     threadStopped();
   }
 }
}

/**
* The Sound class is a container for sound samples. The sound samples are
* format-agnostic and are stored as a byte array.
*/

class Sound {

 private byte[] samples;

 /**
  * Create a new Sound object with the specified byte array. The array is not
  * copied.
  */
 public Sound(byte[] samples) {
   this.samples = samples;
 }

 /**
  * Returns this Sound's objects samples as a byte array.
  */
 public byte[] getSamples() {
   return samples;
 }

}

/**
* The LoopingByteInputStream is a ByteArrayInputStream that loops indefinitly.
* The looping stops when the close() method is called.
* <p>
* Possible ideas to extend this class:
* <ul>
* <li>Add an option to only loop a certain number of times.
* </ul>
*/

class LoopingByteInputStream extends ByteArrayInputStream {

 private boolean closed;

 /**
  * Creates a new LoopingByteInputStream with the specified byte array. The
  * array is not copied.
  */
 public LoopingByteInputStream(byte[] buffer) {
   super(buffer);
   closed = false;
 }

 /**
  * Reads <code>length</code> bytes from the array. If the end of the array
  * is reached, the reading starts over from the beginning of the array.
  * Returns -1 if the array has been closed.
  */
 public int read(byte[] buffer, int offset, int length) {
   if (closed) {
     return -1;
   }
   int totalBytesRead = 0;

   while (totalBytesRead < length) {
     int numBytesRead = super.read(buffer, offset + totalBytesRead,
         length - totalBytesRead);

     if (numBytesRead > 0) {
       totalBytesRead += numBytesRead;
     } else {
       reset();
     }
   }
   return totalBytesRead;
 }

 /**
  * Closes the stream. Future calls to the read() methods will return 1.
  */
 public void close() throws IOException {
   super.close();
   closed = true;
 }

}

/**
* The FilteredSoundStream class is a FilterInputStream that applies a
* SoundFilter to the underlying input stream.
*
* @see SoundFilter
*/

class FilteredSoundStream extends FilterInputStream {

 private static final int REMAINING_SIZE_UNKNOWN = -1;

 private SoundFilter soundFilter;

 private int remainingSize;

 /**
  * Creates a new FilteredSoundStream object with the specified InputStream
  * and SoundFilter.
  */
 public FilteredSoundStream(InputStream in, SoundFilter soundFilter) {
   super(in);
   this.soundFilter = soundFilter;
   remainingSize = REMAINING_SIZE_UNKNOWN;
 }

 /**
  * Overrides the FilterInputStream method to apply this filter whenever
  * bytes are read
  */
 public int read(byte[] samples, int offset, int length) throws IOException {
   // read and filter the sound samples in the stream
   int bytesRead = super.read(samples, offset, length);
   if (bytesRead > 0) {
     soundFilter.filter(samples, offset, bytesRead);
     return bytesRead;
   }

   // if there are no remaining bytes in the sound stream,
   // check if the filter has any remaining bytes ("echoes").
   if (remainingSize == REMAINING_SIZE_UNKNOWN) {
     remainingSize = soundFilter.getRemainingSize();
     // round down to nearest multiple of 4
     // (typical frame size)
     remainingSize = remainingSize / 4 * 4;
   }
   if (remainingSize > 0) {
     length = Math.min(length, remainingSize);

     // clear the buffer
     for (int i = offset; i < offset + length; i++) {
       samples[i] = 0;
     }

     // filter the remaining bytes
     soundFilter.filter(samples, offset, length);
     remainingSize -= length;

     // return
     return length;
   } else {
     // end of stream
     return -1;
   }
 }

}
