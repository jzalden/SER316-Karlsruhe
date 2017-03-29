package net.sf.memoranda.ui;

import java.io.File;
import java.util.Vector;
import java.util.Locale;
import java.util.HashMap;

import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.MimeTypesList;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout.Alignment;

public class PreferencesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8014591076036422134L;

	GridBagConstraints gbc;

	ButtonGroup closeGroup = new ButtonGroup();

	Border rstPanelBorder;

	TitledBorder rsbpBorder;

	TitledBorder titledBorder1;

	ButtonGroup soundGroup = new ButtonGroup();
	Vector<String> fontnames = getFontNames();
	private JTextField tf_browser;
	private JTextField tf_soundFile;

	public PreferencesDialog(Frame frame) {
		super(frame, Local.getString("Preferences"), true);
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	public PreferencesDialog() {
		this(null);
	}

	void jbInit() throws Exception {
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(156, 156, 158)), Local
				.getString("Sound"));
		this.setResizable(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel General = new JPanel();
		tabbedPane.addTab("General", null, General, null);
		tabbedPane.setEnabledAt(0, true);
		GridBagLayout gbl_General = new GridBagLayout();
		gbl_General.columnWidths = new int[]{120, 100, 11, 142, 100, 0};
		gbl_General.rowHeights = new int[]{0, 26, 0, 0, 0, 26, 12, 0};
		gbl_General.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_General.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		General.setLayout(gbl_General);
		
		JLabel lbl_system = new JLabel("System:");
		GridBagConstraints gbc_lbl_system = new GridBagConstraints();
		gbc_lbl_system.anchor = GridBagConstraints.WEST;
		gbc_lbl_system.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_system.gridx = 0;
		gbc_lbl_system.gridy = 0;
		General.add(lbl_system, gbc_lbl_system);
		
		JLabel lbl_lookAndFeel = new JLabel("Look and Feel:");
		GridBagConstraints gbc_lbl_lookAndFeel = new GridBagConstraints();
		gbc_lbl_lookAndFeel.anchor = GridBagConstraints.WEST;
		gbc_lbl_lookAndFeel.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_lookAndFeel.gridx = 3;
		gbc_lbl_lookAndFeel.gridy = 0;
		General.add(lbl_lookAndFeel, gbc_lbl_lookAndFeel);
		
		JLabel lbl_minimizeAction = new JLabel("Minimize Action:");
		GridBagConstraints gbc_lbl_minimizeAction = new GridBagConstraints();
		gbc_lbl_minimizeAction.anchor = GridBagConstraints.EAST;
		gbc_lbl_minimizeAction.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_minimizeAction.gridx = 0;
		gbc_lbl_minimizeAction.gridy = 1;
		General.add(lbl_minimizeAction, gbc_lbl_minimizeAction);
		
		JComboBox<String> cmb_minimizeAction = new JComboBox<String>();
		GridBagConstraints gbc_cmb_minimizeAction = new GridBagConstraints();
		gbc_cmb_minimizeAction.insets = new Insets(0, 0, 5, 5);
		gbc_cmb_minimizeAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmb_minimizeAction.gridx = 1;
		gbc_cmb_minimizeAction.gridy = 1;
		General.add(cmb_minimizeAction, gbc_cmb_minimizeAction);
		
		JLabel lbl_theme = new JLabel("Theme:");
		GridBagConstraints gbc_lbl_theme = new GridBagConstraints();
		gbc_lbl_theme.anchor = GridBagConstraints.EAST;
		gbc_lbl_theme.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_theme.gridx = 3;
		gbc_lbl_theme.gridy = 1;
		General.add(lbl_theme, gbc_lbl_theme);
		
		JComboBox<String> cmb_theme = new JComboBox<String>();
		GridBagConstraints gbc_cmb_theme = new GridBagConstraints();
		gbc_cmb_theme.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_theme.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmb_theme.gridx = 4;
		gbc_cmb_theme.gridy = 1;
		General.add(cmb_theme, gbc_cmb_theme);
		
		JLabel lbl_closeAction = new JLabel("Close Action:");
		GridBagConstraints gbc_lbl_closeAction = new GridBagConstraints();
		gbc_lbl_closeAction.anchor = GridBagConstraints.EAST;
		gbc_lbl_closeAction.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_closeAction.gridx = 0;
		gbc_lbl_closeAction.gridy = 2;
		General.add(lbl_closeAction, gbc_lbl_closeAction);
		
		JComboBox<String> cmb_closeAction = new JComboBox<String>();
		GridBagConstraints gbc_cmb_closeAction = new GridBagConstraints();
		gbc_cmb_closeAction.insets = new Insets(0, 0, 5, 5);
		gbc_cmb_closeAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmb_closeAction.gridx = 1;
		gbc_cmb_closeAction.gridy = 2;
		General.add(cmb_closeAction, gbc_cmb_closeAction);
		
		JLabel lbl_locale = new JLabel("Localization");
		GridBagConstraints gbc_lbl_locale = new GridBagConstraints();
		gbc_lbl_locale.anchor = GridBagConstraints.EAST;
		gbc_lbl_locale.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_locale.gridx = 3;
		gbc_lbl_locale.gridy = 2;
		General.add(lbl_locale, gbc_lbl_locale);
		
		JCheckBox cb_locale = new JCheckBox("");
		GridBagConstraints gbc_cb_locale = new GridBagConstraints();
		gbc_cb_locale.anchor = GridBagConstraints.WEST;
		gbc_cb_locale.insets = new Insets(0, 0, 5, 0);
		gbc_cb_locale.gridx = 4;
		gbc_cb_locale.gridy = 2;
		General.add(cb_locale, gbc_cb_locale);
		
		JLabel lbl_trayIcon = new JLabel("System Tray Icon:");
		GridBagConstraints gbc_lbl_trayIcon = new GridBagConstraints();
		gbc_lbl_trayIcon.anchor = GridBagConstraints.EAST;
		gbc_lbl_trayIcon.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_trayIcon.gridx = 0;
		gbc_lbl_trayIcon.gridy = 3;
		General.add(lbl_trayIcon, gbc_lbl_trayIcon);
		
		JCheckBox cb_trayIcon = new JCheckBox("");
		GridBagConstraints gbc_cb_trayIcon = new GridBagConstraints();
		gbc_cb_trayIcon.anchor = GridBagConstraints.WEST;
		gbc_cb_trayIcon.insets = new Insets(0, 0, 5, 5);
		gbc_cb_trayIcon.gridx = 1;
		gbc_cb_trayIcon.gridy = 3;
		General.add(cb_trayIcon, gbc_cb_trayIcon);
		
		JLabel lbl_language = new JLabel("Language:");
		GridBagConstraints gbc_lbl_language = new GridBagConstraints();
		gbc_lbl_language.anchor = GridBagConstraints.EAST;
		gbc_lbl_language.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_language.gridx = 3;
		gbc_lbl_language.gridy = 3;
		General.add(lbl_language, gbc_lbl_language);
		
		JComboBox<String> cmb_language = new JComboBox<String>();
		GridBagConstraints gbc_cmb_language = new GridBagConstraints();
		gbc_cmb_language.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmb_language.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_language.gridx = 4;
		gbc_cmb_language.gridy = 3;
		General.add(cmb_language, gbc_cmb_language);
		
		JLabel lbl_startMin = new JLabel("Start Minimized:");
		GridBagConstraints gbc_lbl_startMin = new GridBagConstraints();
		gbc_lbl_startMin.anchor = GridBagConstraints.EAST;
		gbc_lbl_startMin.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_startMin.gridx = 0;
		gbc_lbl_startMin.gridy = 4;
		General.add(lbl_startMin, gbc_lbl_startMin);
		
		JCheckBox cb_startMin = new JCheckBox("");
		GridBagConstraints gbc_cb_startMin = new GridBagConstraints();
		gbc_cb_startMin.anchor = GridBagConstraints.WEST;
		gbc_cb_startMin.insets = new Insets(0, 0, 5, 5);
		gbc_cb_startMin.gridx = 1;
		gbc_cb_startMin.gridy = 4;
		General.add(cb_startMin, gbc_cb_startMin);
		
		JLabel lbl_splash = new JLabel("Show Splash Screen:");
		GridBagConstraints gbc_lbl_splash = new GridBagConstraints();
		gbc_lbl_splash.anchor = GridBagConstraints.EAST;
		gbc_lbl_splash.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_splash.gridx = 0;
		gbc_lbl_splash.gridy = 5;
		General.add(lbl_splash, gbc_lbl_splash);
		
		JCheckBox cb_splash = new JCheckBox("");
		GridBagConstraints gbc_cb_splash = new GridBagConstraints();
		gbc_cb_splash.anchor = GridBagConstraints.WEST;
		gbc_cb_splash.insets = new Insets(0, 0, 5, 5);
		gbc_cb_splash.gridx = 1;
		gbc_cb_splash.gridy = 5;
		General.add(cb_splash, gbc_cb_splash);
		
		JLabel lbl_firstDay = new JLabel("First Day of the Week:");
		GridBagConstraints gbc_lbl_firstDay = new GridBagConstraints();
		gbc_lbl_firstDay.anchor = GridBagConstraints.EAST;
		gbc_lbl_firstDay.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_firstDay.gridx = 3;
		gbc_lbl_firstDay.gridy = 5;
		General.add(lbl_firstDay, gbc_lbl_firstDay);
		
		JComboBox<String> cmb_firstDay = new JComboBox<String>();
		GridBagConstraints gbc_cmb_firstDay = new GridBagConstraints();
		gbc_cmb_firstDay.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmb_firstDay.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_firstDay.gridx = 4;
		gbc_cmb_firstDay.gridy = 5;
		General.add(cmb_firstDay, gbc_cmb_firstDay);
		
		JLabel lbl_AskOnExit = new JLabel("Ask on Exit");
		GridBagConstraints gbc_lbl_AskOnExit = new GridBagConstraints();
		gbc_lbl_AskOnExit.anchor = GridBagConstraints.EAST;
		gbc_lbl_AskOnExit.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_AskOnExit.gridx = 0;
		gbc_lbl_AskOnExit.gridy = 6;
		General.add(lbl_AskOnExit, gbc_lbl_AskOnExit);
		
		JCheckBox cb_AskOnExit = new JCheckBox("");
		GridBagConstraints gbc_cb_AskOnExit = new GridBagConstraints();
		gbc_cb_AskOnExit.anchor = GridBagConstraints.WEST;
		gbc_cb_AskOnExit.insets = new Insets(0, 0, 0, 5);
		gbc_cb_AskOnExit.gridx = 1;
		gbc_cb_AskOnExit.gridy = 6;
		General.add(cb_AskOnExit, gbc_cb_AskOnExit);
		
		JPanel Resources = new JPanel();
		tabbedPane.addTab("Resources", null, Resources, null);
		Resources.setLayout(new MigLayout("", "[535.00px,grow]", "[196.00px][35px,grow]"));
		
		ResourceTypePanel resourceTypePanel = new ResourceTypePanel();
		Resources.add(resourceTypePanel, "cell 0 0,grow");
		resourceTypePanel.setBorder(rstPanelBorder);
		
		JPanel panel_1 = new JPanel();
		Resources.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[89px][326px][110px]", "[46px]"));
		
		JLabel lbl_browser = new JLabel();
		lbl_browser.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lbl_browser, "cell 0 0,alignx left,aligny center");
		lbl_browser.setText("Web Browser Path:");
		
		tf_browser = new JTextField();
		panel_1.add(tf_browser, "cell 1 0,growx,aligny center");
		tf_browser.setPreferredSize(new Dimension(250, 25));
		
		JButton but_browser = new JButton();
		panel_1.add(but_browser, "cell 2 0,alignx left,aligny center");
		but_browser.setText("Browse");
		but_browser.setPreferredSize(new Dimension(110, 25));
		
		JPanel Sound = new JPanel();
		tabbedPane.addTab("Sound", null, Sound, null);
		GridBagLayout gbl_Sound = new GridBagLayout();
		gbl_Sound.columnWidths = new int[]{151, 0};
		gbl_Sound.rowHeights = new int[]{0, 194, 0};
		gbl_Sound.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_Sound.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		Sound.setLayout(gbl_Sound);
		
		JCheckBox cb_notifications = new JCheckBox();
		cb_notifications.setText("Enable sound notifications");
		cb_notifications.setSelected(true);
		GridBagConstraints gbc_cb_notifications = new GridBagConstraints();
		gbc_cb_notifications.anchor = GridBagConstraints.WEST;
		gbc_cb_notifications.insets = new Insets(0, 0, 5, 0);
		gbc_cb_notifications.gridx = 0;
		gbc_cb_notifications.gridy = 0;
		Sound.add(cb_notifications, gbc_cb_notifications);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 1;
		Sound.add(panel_5, gbc_panel_5);
		panel_5.setBorder(titledBorder1);
		GridLayout gl_panel_5 = new GridLayout();
		gl_panel_5.setColumns(1);
		gl_panel_5.setRows(4);
		panel_5.setLayout(gl_panel_5);
		
		JRadioButton rb_default = new JRadioButton();
		soundGroup.add(rb_default);
		panel_5.add(rb_default);
		rb_default.setText("Default");
		rb_default.setEnabled(true);
		
		JRadioButton rb_beep = new JRadioButton();
		soundGroup.add(rb_beep);
		panel_5.add(rb_beep);
		rb_beep.setText("System beep");
		rb_beep.setEnabled(true);
		
		JRadioButton rb_custom = new JRadioButton();
		soundGroup.add(rb_custom);
		panel_5.add(rb_custom);
		rb_custom.setText("Custom");
		rb_custom.setEnabled(true);
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new MigLayout("", "[51px][231.00px][67px]", "[23px]"));
		
		JLabel lbl_soundFile = new JLabel();
		lbl_soundFile.setText("Sound file:");
		lbl_soundFile.setEnabled(true);
		panel_6.add(lbl_soundFile, "cell 0 0,alignx left,aligny center");
		
		tf_soundFile = new JTextField();
		tf_soundFile.setHorizontalAlignment(SwingConstants.CENTER);
		tf_soundFile.setEnabled(true);
		panel_6.add(tf_soundFile, "cell 1 0,growx,aligny center");
		
		JButton but_soundFile = new JButton();
		but_soundFile.setText("Browse");
		but_soundFile.setEnabled(true);
		panel_6.add(but_soundFile, "cell 2 0,alignx left,aligny top");
		
		JPanel Editor = new JPanel();
		tabbedPane.addTab("Editor", null, Editor, null);
		GridBagLayout gbl_Editor = new GridBagLayout();
		gbl_Editor.columnWidths = new int[]{105, 0, 0};
		gbl_Editor.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_Editor.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_Editor.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		Editor.setLayout(gbl_Editor);
		
		JLabel lbl_normalFont = new JLabel("Normal Font:");
		GridBagConstraints gbc_lbl_normalFont = new GridBagConstraints();
		gbc_lbl_normalFont.anchor = GridBagConstraints.EAST;
		gbc_lbl_normalFont.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_normalFont.gridx = 0;
		gbc_lbl_normalFont.gridy = 0;
		Editor.add(lbl_normalFont, gbc_lbl_normalFont);
		
		JComboBox<String> cmb_normalFont = new JComboBox<String>();
		GridBagConstraints gbc_cmb_normalFont = new GridBagConstraints();
		gbc_cmb_normalFont.anchor = GridBagConstraints.WEST;
		gbc_cmb_normalFont.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_normalFont.gridx = 1;
		gbc_cmb_normalFont.gridy = 0;
		Editor.add(cmb_normalFont, gbc_cmb_normalFont);
		
		JLabel lbl_headerFont = new JLabel("Header Font:");
		GridBagConstraints gbc_lbl_headerFont = new GridBagConstraints();
		gbc_lbl_headerFont.anchor = GridBagConstraints.EAST;
		gbc_lbl_headerFont.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_headerFont.gridx = 0;
		gbc_lbl_headerFont.gridy = 1;
		Editor.add(lbl_headerFont, gbc_lbl_headerFont);
		
		JComboBox<String> cmb_headerFont = new JComboBox<String>();
		GridBagConstraints gbc_cmb_headerFont = new GridBagConstraints();
		gbc_cmb_headerFont.anchor = GridBagConstraints.WEST;
		gbc_cmb_headerFont.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_headerFont.gridx = 1;
		gbc_cmb_headerFont.gridy = 1;
		Editor.add(cmb_headerFont, gbc_cmb_headerFont);
		
		JLabel lbl_monoFont = new JLabel("Monospaced Font:");
		GridBagConstraints gbc_lbl_monoFont = new GridBagConstraints();
		gbc_lbl_monoFont.anchor = GridBagConstraints.EAST;
		gbc_lbl_monoFont.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_monoFont.gridx = 0;
		gbc_lbl_monoFont.gridy = 2;
		Editor.add(lbl_monoFont, gbc_lbl_monoFont);
		
		JComboBox<String> cmb_monoFont = new JComboBox<String>();
		GridBagConstraints gbc_cmb_monoFont = new GridBagConstraints();
		gbc_cmb_monoFont.anchor = GridBagConstraints.WEST;
		gbc_cmb_monoFont.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_monoFont.gridx = 1;
		gbc_cmb_monoFont.gridy = 2;
		Editor.add(cmb_monoFont, gbc_cmb_monoFont);
		
		JLabel lbl_fontSize = new JLabel("Font Size:");
		GridBagConstraints gbc_lbl_fontSize = new GridBagConstraints();
		gbc_lbl_fontSize.anchor = GridBagConstraints.EAST;
		gbc_lbl_fontSize.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_fontSize.gridx = 0;
		gbc_lbl_fontSize.gridy = 3;
		Editor.add(lbl_fontSize, gbc_lbl_fontSize);
		
		JSpinner cmb_fontSize = new JSpinner();
		GridBagConstraints gbc_cmb_fontSize = new GridBagConstraints();
		gbc_cmb_fontSize.insets = new Insets(0, 0, 5, 0);
		gbc_cmb_fontSize.anchor = GridBagConstraints.WEST;
		gbc_cmb_fontSize.gridx = 1;
		gbc_cmb_fontSize.gridy = 3;
		Editor.add(cmb_fontSize, gbc_cmb_fontSize);
		
		JLabel lbl_antialiasing = new JLabel("Antialiasing:");
		GridBagConstraints gbc_lbl_antialiasing = new GridBagConstraints();
		gbc_lbl_antialiasing.anchor = GridBagConstraints.EAST;
		gbc_lbl_antialiasing.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_antialiasing.gridx = 0;
		gbc_lbl_antialiasing.gridy = 4;
		Editor.add(lbl_antialiasing, gbc_lbl_antialiasing);
		
		JCheckBox cb_antialiasing = new JCheckBox("");
		GridBagConstraints gbc_cb_antialiasing = new GridBagConstraints();
		gbc_cb_antialiasing.anchor = GridBagConstraints.WEST;
		gbc_cb_antialiasing.gridx = 1;
		gbc_cb_antialiasing.gridy = 4;
		Editor.add(cb_antialiasing, gbc_cb_antialiasing);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton but_OK = new JButton("OK");
		closeGroup.add(but_OK);
		but_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(but_OK);
		
		JButton but_cancel = new JButton("Cancel");
		closeGroup.add(but_cancel);
		panel.add(but_cancel);
		
		JButton but_apply = new JButton("Apply");
		closeGroup.add(but_apply);
		panel.add(but_apply);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(2, 0, 0, 10);
		gbc.anchor = GridBagConstraints.WEST;
		// populate with languages
		HashMap<String, String> languageTags = Local.languageTags;
		for (String s : languageTags.keySet()) {
			languageCB.addItem(s);
		}

		// Build Tab2
		rstPanelBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		rsbpBorder = new TitledBorder(BorderFactory.createEmptyBorder(5, 5, 5,
				5), Local.getString("Web browser executable"));

		// set all config-values
		setValues();

	}

	void setValues() {
		// language
		String item;
		for (int i = 0; i < languageCB.getItemCount(); i++) {
			item = (String) languageCB.getItemAt(i);
			if (Local.languageTags.get(item).equalsIgnoreCase(Configuration.get("LANGUAGE").toString())) {
				languageCB.setSelectedIndex(i);
				break;
			}
		}
		if (!Configuration.get("DISABLE_L10N").toString().equalsIgnoreCase("yes")) {
			languageCB.setEnabled(false);
		}

		enableCustomLF(false);
		String lf = Configuration.get("LOOK_AND_FEEL").toString();
		if (lf.equalsIgnoreCase("system"))
			lfSystemRB.setSelected(true);
		else if (lf.equalsIgnoreCase("default"))
			lfJavaRB.setSelected(true);
		else if (lf.length() > 0) {
			lfCustomRB.setSelected(true);
			enableCustomLF(true);
			lfClassName.setText(lf);
		} else
			lfJavaRB.setSelected(true);
		String onclose = Configuration.get("ON_CLOSE").toString();
		if (onclose.equals("exit")) {
			this.closeExitRB.setSelected(true);
			// this.askConfirmChB.setEnabled(true);
		} else {
			this.closeHideRB.setSelected(true);
			// this.askConfirmChB.setEnabled(false);
		}

		String onmin = Configuration.get("ON_MINIMIZE").toString();

		if (!System.getProperty("os.name").startsWith("Win"))
			this.browserPath.setText(MimeTypesList.getAppList()
					.getBrowserExec());
		if (Configuration.get("NOTIFY_SOUND").equals("")) {
			Configuration.put("NOTIFY_SOUND", "DEFAULT");
		}

		boolean enableSnd = !Configuration.get("NOTIFY_SOUND").toString()
				.equalsIgnoreCase("DISABLED");
		if (Configuration.get("NOTIFY_SOUND").toString().equalsIgnoreCase(
				"DEFAULT")
				|| Configuration.get("NOTIFY_SOUND").toString()
						.equalsIgnoreCase("DISABLED")) {
			this.soundDefaultRB.setSelected(true);
			this.enableCustomSound(false);
		} else if (Configuration.get("NOTIFY_SOUND").toString()
				.equalsIgnoreCase("BEEP")) {
			this.soundBeepRB.setSelected(true);
			this.enableCustomSound(false);
		} else {
			System.out.println(Configuration.get("NOTIFY_SOUND").toString());
			this.soundCustomRB.setSelected(true);
			this.soundFile
					.setText(Configuration.get("NOTIFY_SOUND").toString());
			this.enableCustomSound(true);
		}
		this.enableSound(enableSnd);
		if (Configuration.get("NORMAL_FONT").toString().length() >0)
			normalFontCB.setSelectedItem(Configuration.get("NORMAL_FONT").toString());
		else
			normalFontCB.setSelectedItem("serif");
		if (Configuration.get("HEADER_FONT").toString().length() >0)
			headerFontCB.setSelectedItem(Configuration.get("HEADER_FONT").toString());
		else
			headerFontCB.setSelectedItem("sans-serif");
		if (Configuration.get("MONO_FONT").toString().length() >0)
			monoFontCB.setSelectedItem(Configuration.get("MONO_FONT").toString());
		else
			monoFontCB.setSelectedItem("monospaced");
		if (Configuration.get("BASE_FONT_SIZE").toString().length() >0)
			baseFontSize.setValue(Integer.decode(Configuration.get("BASE_FONT_SIZE").toString()));
		else
			baseFontSize.setValue(new Integer(16));
	}

	void apply() {
		if (this.firstdow.isSelected())
			Configuration.put("FIRST_DAY_OF_WEEK", "mon");
		else
			Configuration.put("FIRST_DAY_OF_WEEK", "sun");

		if (this.enL10nChB.isSelected())
			Configuration.put("DISABLE_L10N", "no");
		else
			Configuration.put("DISABLE_L10N", "yes");

		if (this.enSplashChB.isSelected())
			Configuration.put("SHOW_SPLASH", "yes");
		else
			Configuration.put("SHOW_SPLASH", "no");

		if (this.enSystrayChB.isSelected())
			Configuration.put("DISABLE_SYSTRAY", "no");
		else
			Configuration.put("DISABLE_SYSTRAY", "yes");

		if (this.startMinimizedChB.isSelected())
			Configuration.put("START_MINIMIZED", "yes");
		else
			Configuration.put("START_MINIMIZED", "no");

		if (this.askConfirmChB.isSelected())
			Configuration.put("ASK_ON_EXIT", "yes");
		else
			Configuration.put("ASK_ON_EXIT", "no");

		if (this.closeExitRB.isSelected())
			Configuration.put("ON_CLOSE", "exit");
		else
			Configuration.put("ON_CLOSE", "minimize");

		Configuration.put("ON_MINIMIZE", "normal");

		String lf = Configuration.get("LOOK_AND_FEEL").toString();
		String newlf = "";

		if (this.lfSystemRB.isSelected())
			newlf = "system";
		else if (this.lfJavaRB.isSelected())
			newlf = "default";
		else if (this.lfCustomRB.isSelected())
			newlf = this.lfClassName.getText();

		if (!lf.equalsIgnoreCase(newlf)) {
			Configuration.put("LOOK_AND_FEEL", newlf);
			try {
				if (Configuration.get("LOOK_AND_FEEL").equals("system"))
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());
				else if (Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
					UIManager.setLookAndFeel(Configuration.get("LOOK_AND_FEEL")
							.toString());

				SwingUtilities.updateComponentTreeUI(App.getFrame());

			} catch (Exception e) {
				Configuration.put("LOOK_AND_FEEL", lf);
				new ExceptionDialog(
						e,
						"Error when initializing a pluggable look-and-feel. Default LF will be used.",
						"Make sure that specified look-and-feel library classes are on the CLASSPATH.");
			}
		}
		String brPath = this.browserPath.getText();
		if (new java.io.File(brPath).isFile()) {
			MimeTypesList.getAppList().setBrowserExec(brPath);
			CurrentStorage.get().storeMimeTypesList();
		}

		if (!this.enableSoundCB.isSelected())
			Configuration.put("NOTIFY_SOUND", "DISABLED");
		else if (this.soundDefaultRB.isSelected())
			Configuration.put("NOTIFY_SOUND", "DEFAULT");
		else if (this.soundBeepRB.isSelected())
			Configuration.put("NOTIFY_SOUND", "BEEP");
		else if ((this.soundCustomRB.isSelected())
				&& (this.soundFile.getText().trim().length() > 0))
			Configuration.put("NOTIFY_SOUND", this.soundFile.getText().trim());

		if (antialiasChB.isSelected())
			Configuration.put("ANTIALIAS_TEXT", "yes");
		else
			Configuration.put("ANTIALIAS_TEXT", "no");

		if (enL10nChB.isSelected()) {
			Configuration.put("DISABLE_L10N", "no");
		}
		else {
			Configuration.put("DISABLE_L10N", "yes");
		}
		Configuration.put("LANGUAGE", Local.languageTags.get(languageCB.getSelectedItem().toString()));

		Configuration.put("NORMAL_FONT", normalFontCB.getSelectedItem());
		Configuration.put("HEADER_FONT", headerFontCB.getSelectedItem());
		Configuration.put("MONO_FONT", monoFontCB.getSelectedItem());
		Configuration.put("BASE_FONT_SIZE", baseFontSize.getValue());
		App.getFrame().workPanel.dailyItemsPanel.editorPanel.editor.editor.setAntiAlias(antialiasChB.isSelected());
		App.getFrame().workPanel.dailyItemsPanel.editorPanel.initCSS();
		App.getFrame().workPanel.dailyItemsPanel.editorPanel.editor.repaint();

		Configuration.saveConfig();

	}

	void enableCustomLF(boolean is) {
	}

	void enableCustomSound(boolean is) {
	}

	void enableSound(boolean is) {
		enableCustomSound(is);

	}

	void lfSystemRB_actionPerformed(ActionEvent e) {
		this.enableCustomLF(false);
	}

	void lfJavaRB_actionPerformed(ActionEvent e) {
		this.enableCustomLF(false);
	}

	Vector<String> getFontNames() {
		GraphicsEnvironment gEnv =
        	GraphicsEnvironment.getLocalGraphicsEnvironment();
        String envfonts[] = gEnv.getAvailableFontFamilyNames();
        Vector<String> fonts = new Vector<String>();
        fonts.add("serif");
        fonts.add("sans-serif");
        fonts.add("monospaced");
        for (int i = 0; i < envfonts.length; i++)
            fonts.add(envfonts[i]);
		return fonts;
	}
}
