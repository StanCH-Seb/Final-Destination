package game.ui;

import game.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameWindow extends JFrame {

    // ── Colors ─────────────────────────────────────────────────────────
    private static final Color BG_DARK      = new Color(10, 8, 12);
    private static final Color BG_PANEL     = new Color(18, 14, 22);
    private static final Color BG_TEXT      = new Color(24, 19, 30);
    private static final Color ACCENT_RED   = new Color(180, 30, 40);
    private static final Color ACCENT_DIM   = new Color(100, 20, 28);
    private static final Color TEXT_MAIN    = new Color(220, 210, 200);
    private static final Color TEXT_DIM     = new Color(130, 120, 115);
    private static final Color TEXT_HINT    = new Color(160, 140, 80);
    private static final Color BTN_NORMAL   = new Color(30, 22, 38);
    private static final Color BTN_HOVER    = new Color(50, 35, 55);
    private static final Color BTN_BORDER   = new Color(70, 50, 80);
    private static final Color DEATH_RED    = new Color(160, 20, 30);
    private static final Color ENDING_GOLD  = new Color(180, 150, 60);

    // ── Fonts ───────────────────────────────────────────────────────────
    private Font fontTitle;
    private Font fontBody;
    private Font fontBtn;
    private Font fontMono;
    private Font fontSmall;

    // ── State ───────────────────────────────────────────────────────────
    private String playerName = "Player";
    private Scene[] scenes;
    private String currentSceneId = "MENU";
    private int deathCount = 0;
    private javax.swing.Timer typewriterTimer;
    private int typewriterIndex = 0;
    private String typewriterText = "";
    private boolean typewriterDone = false;

    // ── UI Components ───────────────────────────────────────────────────
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Menu screen
    private JPanel menuScreen;

    // Name screen
    private JPanel nameScreen;
    private JTextField nameField;

    // Game screen
    private JPanel gameScreen;
    private JLabel sceneTypeLabel;
    private JTextArea narrativeArea;
    private JPanel choicesPanel;
    private JButton hintButton;
    private JLabel hintLabel;
    private JPanel deathOverlay;
    private JPanel endingOverlay;

    // Log screen
    private JPanel logScreen;
    private JTextArea logArea;

    public GameWindow() {
        loadFonts();
        initFrame();
        buildMenuScreen();
        buildNameScreen();
        buildGameScreen();
        buildLogScreen();
        showScreen("MENU");
        setVisible(true);
    }

    // ── Font loading ────────────────────────────────────────────────────
    private void loadFonts() {
        try {
            fontTitle = new Font("Georgia", Font.BOLD, 32);
            fontBody  = new Font("Georgia", Font.PLAIN, 15);
            fontBtn   = new Font("Courier New", Font.BOLD, 13);
            fontMono  = new Font("Courier New", Font.PLAIN, 13);
            fontSmall = new Font("Courier New", Font.PLAIN, 11);
        } catch (Exception e) {
            fontTitle = new Font(Font.SERIF, Font.BOLD, 32);
            fontBody  = new Font(Font.SERIF, Font.PLAIN, 15);
            fontBtn   = new Font(Font.MONOSPACED, Font.BOLD, 13);
            fontMono  = new Font(Font.MONOSPACED, Font.PLAIN, 13);
            fontSmall = new Font(Font.MONOSPACED, Font.PLAIN, 11);
        }
    }

    // ── Frame setup ─────────────────────────────────────────────────────
    private void initFrame() {
        setTitle("Final Destination");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 620);
        setMinimumSize(new Dimension(680, 520));
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);
        mainPanel.setBackground(BG_DARK);
        setContentPane(mainPanel);
    }

    private void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    // ════════════════════════════════════════════════════════════════════
    // MENU SCREEN
    // ════════════════════════════════════════════════════════════════════
    private void buildMenuScreen() {
        menuScreen = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintAtmosphere(g, getWidth(), getHeight());
            }
        };
        menuScreen.setBackground(BG_DARK);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);

        // Title
        JLabel skull = makeLabel("☠", new Font("Dialog", Font.PLAIN, 56), ACCENT_RED);
        skull.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = makeLabel("FINAL DESTINATION", fontTitle, TEXT_MAIN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = makeLabel("A survival story where every choice matters", fontSmall, TEXT_DIM);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(ACCENT_DIM);
        sep.setMaximumSize(new Dimension(300, 2));

        JButton startBtn  = makeMenuButton("A  ·  START GAME");
        JButton logBtn    = makeMenuButton("B  ·  VIEW PAST SESSIONS");
        JButton quitBtn   = makeMenuButton("C  ·  QUIT");

        startBtn.addActionListener(e -> showScreen("NAME"));
        logBtn.addActionListener(e -> {
            refreshLog();
            showScreen("LOG");
        });
        quitBtn.addActionListener(e -> System.exit(0));

        inner.add(Box.createVerticalStrut(20));
        inner.add(skull);
        inner.add(Box.createVerticalStrut(8));
        inner.add(title);
        inner.add(Box.createVerticalStrut(6));
        inner.add(sub);
        inner.add(Box.createVerticalStrut(24));
        inner.add(sep);
        inner.add(Box.createVerticalStrut(24));
        inner.add(startBtn);
        inner.add(Box.createVerticalStrut(10));
        inner.add(logBtn);
        inner.add(Box.createVerticalStrut(10));
        inner.add(quitBtn);
        inner.add(Box.createVerticalStrut(20));

        menuScreen.add(inner);
        mainPanel.add(menuScreen, "MENU");
    }

    // ════════════════════════════════════════════════════════════════════
    // NAME SCREEN
    // ════════════════════════════════════════════════════════════════════
    private void buildNameScreen() {
        nameScreen = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintAtmosphere(g, getWidth(), getHeight());
            }
        };
        nameScreen.setBackground(BG_DARK);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
        inner.setMaximumSize(new Dimension(400, 400));

        JLabel prompt = makeLabel("Who are you?", new Font("Georgia", Font.BOLD, 22), TEXT_MAIN);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = makeLabel("Death needs a name.", fontSmall, TEXT_DIM);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField(20);
        nameField.setFont(fontMono);
        nameField.setBackground(BG_TEXT);
        nameField.setForeground(TEXT_MAIN);
        nameField.setCaretColor(ACCENT_RED);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BTN_BORDER, 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        nameField.setMaximumSize(new Dimension(320, 44));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setHorizontalAlignment(JTextField.CENTER);

        JButton continueBtn = makeMenuButton("CONTINUE  →");
        continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        ActionListener startAction = e -> {
            String n = nameField.getText().trim();
            playerName = n.isEmpty() ? "Player" : n;
            nameField.setText("");
            startNewGame();
        };
        continueBtn.addActionListener(startAction);
        nameField.addActionListener(startAction);

        JButton backBtn = makeSmallLink("← Back to menu");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> showScreen("MENU"));

        inner.add(Box.createVerticalStrut(10));
        inner.add(prompt);
        inner.add(Box.createVerticalStrut(6));
        inner.add(sub);
        inner.add(Box.createVerticalStrut(28));
        inner.add(nameField);
        inner.add(Box.createVerticalStrut(16));
        inner.add(continueBtn);
        inner.add(Box.createVerticalStrut(12));
        inner.add(backBtn);

        nameScreen.add(inner);
        mainPanel.add(nameScreen, "NAME");
    }

    // ════════════════════════════════════════════════════════════════════
    // GAME SCREEN
    // ════════════════════════════════════════════════════════════════════
    private void buildGameScreen() {
        gameScreen = new JPanel(new BorderLayout(0, 0));
        gameScreen.setBackground(BG_DARK);

        // ── Top bar ──
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_DARK);
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 8, 20));

        sceneTypeLabel = makeLabel("[STORY]", fontSmall, TEXT_DIM);
        JLabel gameTitle = makeLabel("FINAL DESTINATION", new Font("Courier New", Font.BOLD, 12), ACCENT_RED);

        topBar.add(sceneTypeLabel, BorderLayout.WEST);
        topBar.add(gameTitle, BorderLayout.EAST);

        // thin red line
        JPanel redLine = new JPanel();
        redLine.setBackground(ACCENT_DIM);
        redLine.setPreferredSize(new Dimension(0, 1));

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(BG_DARK);
        topSection.add(topBar, BorderLayout.CENTER);
        topSection.add(redLine, BorderLayout.SOUTH);

        // ── Narrative area ──
        narrativeArea = new JTextArea();
        narrativeArea.setFont(fontBody);
        narrativeArea.setForeground(TEXT_MAIN);
        narrativeArea.setBackground(BG_DARK);
        narrativeArea.setEditable(false);
        narrativeArea.setLineWrap(true);
        narrativeArea.setWrapStyleWord(true);
        narrativeArea.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));
        narrativeArea.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(narrativeArea);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BG_DARK);
        scrollPane.getViewport().setBackground(BG_DARK);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Style scrollbar
        scrollPane.getVerticalScrollBar().setBackground(BG_DARK);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = BTN_BORDER; trackColor = BG_DARK;
            }
        });

        // ── Bottom panel (hint + choices) ──
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(BG_PANEL);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, ACCENT_DIM),
                BorderFactory.createEmptyBorder(14, 20, 16, 20)
        ));

        // hint row
        JPanel hintRow = new JPanel(new BorderLayout(10, 0));
        hintRow.setOpaque(false);
        hintButton = makeSmallLink("💡 Hint");
        hintLabel  = makeLabel("", fontSmall, TEXT_HINT);
        hintLabel.setFont(new Font("Georgia", Font.ITALIC, 13));
        hintRow.add(hintButton, BorderLayout.WEST);
        hintRow.add(hintLabel, BorderLayout.CENTER);

        // choices panel
        choicesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        choicesPanel.setOpaque(false);

        bottomPanel.add(hintRow,      BorderLayout.NORTH);
        bottomPanel.add(choicesPanel, BorderLayout.CENTER);

        // Assemble
        gameScreen.add(topSection,  BorderLayout.NORTH);
        gameScreen.add(scrollPane,  BorderLayout.CENTER);
        gameScreen.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(gameScreen, "GAME");
    }

    // ════════════════════════════════════════════════════════════════════
    // LOG SCREEN
    // ════════════════════════════════════════════════════════════════════
    private void buildLogScreen() {
        logScreen = new JPanel(new BorderLayout());
        logScreen.setBackground(BG_DARK);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(16, 20, 10, 20));

        JLabel title = makeLabel("PAST SESSIONS", new Font("Georgia", Font.BOLD, 18), TEXT_MAIN);
        JButton back = makeSmallLink("← Back");
        back.addActionListener(e -> showScreen("MENU"));

        header.add(title, BorderLayout.WEST);
        header.add(back,  BorderLayout.EAST);

        JPanel divider = new JPanel();
        divider.setBackground(ACCENT_DIM);
        divider.setPreferredSize(new Dimension(0, 1));

        logArea = new JTextArea();
        logArea.setFont(fontMono);
        logArea.setForeground(TEXT_DIM);
        logArea.setBackground(BG_DARK);
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JScrollPane sp = new JScrollPane(logArea);
        sp.setBorder(null);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_DARK);

        logScreen.add(header,  BorderLayout.NORTH);
        logScreen.add(divider, BorderLayout.CENTER);
        logScreen.add(sp,      BorderLayout.CENTER);

        // fix layout — header north, divider+sp center
        logScreen.remove(divider);
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BG_DARK);
        content.add(divider, BorderLayout.NORTH);
        content.add(sp, BorderLayout.CENTER);
        logScreen.add(content, BorderLayout.CENTER);

        mainPanel.add(logScreen, "LOG");
    }

    private void refreshLog() {
        StringBuilder sb = new StringBuilder();
        java.io.File file = new java.io.File("game_log.txt");
        if (!file.exists()) {
            logArea.setText("  No sessions recorded yet. Play a round first!");
            return;
        }
        try (java.util.Scanner sc = new java.util.Scanner(file)) {
            sb.append("  ").append("─".repeat(60)).append("\n");
            while (sc.hasNextLine()) {
                sb.append("  ").append(sc.nextLine()).append("\n");
            }
            sb.append("  ").append("─".repeat(60));
        } catch (Exception e) {
            sb.append("  Could not read log: ").append(e.getMessage());
        }
        logArea.setText(sb.toString());
        logArea.setCaretPosition(0);
    }

    // ════════════════════════════════════════════════════════════════════
    // GAME LOGIC
    // ════════════════════════════════════════════════════════════════════
    private void startNewGame() {
        scenes     = StoryBuilder.buildScenes(playerName);
        deathCount = 0;
        showScreen("GAME");
        loadScene("PREAMBLE");
    }

    private Scene getScene(String id) {
        for (Scene s : scenes) {
            if (s != null && s.getId().equals(id)) return s;
        }
        return null;
    }

    private void loadScene(String id) {
        if (id == null || id.equals("MENU")) {
            showScreen("MENU");
            return;
        }

        Scene scene = getScene(id);
        if (scene == null) return;

        currentSceneId = id;

        // Update top label
        String type = scene.getSceneType();
        Color labelColor = type.equals("DEATH") ? DEATH_RED
                : type.equals("ENDING") ? ENDING_GOLD
                : TEXT_DIM;
        sceneTypeLabel.setText("[" + type + "]");
        sceneTypeLabel.setForeground(labelColor);
        narrativeArea.setForeground(type.equals("DEATH") ? new Color(210, 170, 165) : TEXT_MAIN);

        // Clear choices & hint
        choicesPanel.removeAll();
        hintLabel.setText("");
        hintButton.setVisible(false);

        // Typewrite narrative
        startTypewriter(scene.getNarrative(), () -> {
            if (scene.isGameOver()) {
                deathCount++;
                GameLogger.logSession(playerName, deathCount, "DIED");
                showDeathButtons();
            } else if (scene.isEnding()) {
                GameLogger.logSession(playerName, deathCount, "SURVIVED");
                narrativeArea.setForeground(ENDING_GOLD);
                showEndingButtons();
            } else {
                showChoices(scene);
            }
        });
    }

    private void showChoices(Scene scene) {
        choicesPanel.removeAll();

        List<game.logic.Choice> choices = scene.getChoices();
        for (game.logic.Choice c : choices) {
            JButton btn = makeChoiceButton(c.getLabel(), c.getText());
            btn.addActionListener(e -> loadScene(c.getNextSceneId()));
            choicesPanel.add(btn);
        }

        if (scene.getHint() != null && !scene.getHint().isEmpty()) {
            hintButton.setVisible(true);
            hintButton.addActionListener(null);
            // remove old listeners first
            for (ActionListener al : hintButton.getActionListeners())
                hintButton.removeActionListener(al);
            String hint = scene.getHint();
            hintButton.addActionListener(e -> {
                hintLabel.setText("  " + hint);
                hintButton.setVisible(false);
            });
        }

        choicesPanel.revalidate();
        choicesPanel.repaint();
    }

    private void showDeathButtons() {
        choicesPanel.removeAll();
        JButton retry = makeChoiceButton("↺", "Try Again");
        JButton menu  = makeChoiceButton("⌂", "Main Menu");
        retry.addActionListener(e -> startNewGame());
        menu.addActionListener(e -> showScreen("MENU"));
        choicesPanel.add(retry);
        choicesPanel.add(menu);
        choicesPanel.revalidate();
        choicesPanel.repaint();
    }

    private void showEndingButtons() {
        choicesPanel.removeAll();
        JButton menu = makeChoiceButton("⌂", "Main Menu");
        menu.addActionListener(e -> showScreen("MENU"));
        choicesPanel.add(menu);
        choicesPanel.revalidate();
        choicesPanel.repaint();
    }

    // ── Typewriter effect ────────────────────────────────────────────────
    private void startTypewriter(String text, Runnable onDone) {
        if (typewriterTimer != null && typewriterTimer.isRunning()) {
            typewriterTimer.stop();
        }
        typewriterText  = text;
        typewriterIndex = 0;
        narrativeArea.setText("");
        typewriterDone  = false;

        typewriterTimer = new javax.swing.Timer(18, null);
        typewriterTimer.addActionListener(e -> {
            if (typewriterIndex < typewriterText.length()) {
                narrativeArea.append(String.valueOf(typewriterText.charAt(typewriterIndex)));
                typewriterIndex++;
                // scroll to bottom
                narrativeArea.setCaretPosition(narrativeArea.getDocument().getLength());
            } else {
                typewriterTimer.stop();
                typewriterDone = true;
                if (onDone != null) SwingUtilities.invokeLater(onDone);
            }
        });
        typewriterTimer.start();

        // click to skip typewriter
        narrativeArea.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (!typewriterDone && typewriterTimer.isRunning()) {
                    typewriterTimer.stop();
                    narrativeArea.setText(typewriterText);
                    narrativeArea.setCaretPosition(narrativeArea.getDocument().getLength());
                    typewriterDone = true;
                    if (onDone != null) SwingUtilities.invokeLater(onDone);
                }
            }
        });
    }

    // ════════════════════════════════════════════════════════════════════
    // HELPERS — Widget builders
    // ════════════════════════════════════════════════════════════════════
    private JLabel makeLabel(String text, Font font, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(color);
        return l;
    }

    private JButton makeMenuButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? BTN_HOVER : BTN_NORMAL;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(BTN_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(fontBtn);
        btn.setForeground(TEXT_MAIN);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 44));
        btn.setMaximumSize(new Dimension(280, 44));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private JButton makeChoiceButton(String label, String text) {
        JButton btn = new JButton() {
            boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = hovered ? new Color(60, 40, 70) : new Color(30, 22, 38);
                Color border = hovered ? ACCENT_RED : BTN_BORDER;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(border);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                // label box
                g2.setColor(hovered ? ACCENT_RED : ACCENT_DIM);
                g2.fillRoundRect(8, getHeight()/2-11, 26, 22, 4, 4);
                g2.setFont(new Font("Courier New", Font.BOLD, 12));
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int lx = 8 + (26 - fm.stringWidth(label)) / 2;
                int ly = getHeight()/2 + fm.getAscent()/2 - 2;
                g2.drawString(label, lx, ly);
                // text
                g2.setFont(new Font("Georgia", Font.PLAIN, 13));
                g2.setColor(hovered ? TEXT_MAIN : new Color(190, 180, 175));
                g2.drawString(text, 42, getHeight()/2 + g2.getFontMetrics().getAscent()/2 - 2);
                g2.dispose();
            }
        };
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        FontMetrics fm = btn.getFontMetrics(new Font("Georgia", Font.PLAIN, 13));
        int w = Math.max(fm.stringWidth(text) + 60, 130);
        btn.setPreferredSize(new Dimension(w, 40));
        return btn;
    }

    private JButton makeSmallLink(String text) {
        JButton btn = new JButton(text);
        btn.setFont(fontSmall);
        btn.setForeground(TEXT_DIM);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(TEXT_MAIN); }
            public void mouseExited(MouseEvent e)  { btn.setForeground(TEXT_DIM);  }
        });
        return btn;
    }

    // ── Background atmosphere ────────────────────────────────────────────
    private void paintAtmosphere(Graphics g, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        // base
        g2.setColor(BG_DARK);
        g2.fillRect(0, 0, w, h);
        // subtle radial vignette from top
        GradientPaint gp = new GradientPaint(
                w/2f, 0, new Color(60, 10, 15, 60),
                w/2f, h, new Color(0, 0, 0, 0)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        // faint horizontal lines
        g2.setColor(new Color(255, 255, 255, 4));
        for (int y = 0; y < h; y += 4) g2.drawLine(0, y, w, y);
        g2.dispose();
    }
}