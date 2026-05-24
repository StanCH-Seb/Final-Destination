package ui.gui;

import game.Choice;
import game.Scene;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * ActiveGamePanel — the panel shown during an active game session.
 *
 * Layout (BorderLayout):
 *   ┌──────────────────────────────────────┐
 *   │  CENTER: JScrollPane                 │
 *   │    └─ JTextArea (scene narrative)    │
 *   ├──────────────────────────────────────┤
 *   │  SOUTH: choicePanel (GridLayout)     │
 *   │    └─ JButton per Choice             │
 *   │    └─ OR single "Return to Menu" btn │
 *   └──────────────────────────────────────┘
 *
 * The key public method is {@link #updateDisplay(Scene)}, which is called by
 * GuiGameEngine every time the player navigates to a new scene.
 */
public class ActiveGamePanel extends JPanel {

    // ── Colour palette (matches MainMenuPanel) ───────────────────────────────
    private static final Color BG_COLOR        = new Color(18, 18, 18);
    private static final Color NARRATIVE_BG    = new Color(24, 24, 22);
    private static final Color NARRATIVE_FG    = new Color(210, 210, 200);
    private static final Color CHOICE_BG       = new Color(38, 38, 38);
    private static final Color CHOICE_FG       = new Color(200, 200, 190);
    private static final Color CHOICE_HOVER_BG = new Color(60, 60, 55);
    private static final Color GAMEOVER_BG     = new Color(90, 30, 30);
    private static final Color GAMEOVER_FG     = new Color(240, 200, 200);
    private static final Color ENDING_BG       = new Color(28, 60, 38);
    private static final Color ENDING_FG       = new Color(200, 240, 210);

    // ── Reference back to the controller ────────────────────────────────────
    private final GuiGameEngine engine;

    // ── Swing components owned by this panel ────────────────────────────────

    /**
     * Displays the scene's narrative text.
     * Non-editable, word-wrapped, monospaced for the terminal feel.
     */
    private final JTextArea narrativeArea;

    /**
     * Wraps narrativeArea with scroll support for long passages.
     */
    private final JScrollPane narrativeScroll;

    /**
     * Dynamically repopulated with one JButton per Choice on every scene load.
     * Uses GridLayout(0, 1) — one column, variable rows.
     */
    private final JPanel choicePanel;

    // ────────────────────────────────────────────────────────────────────────
    // Constructor
    // ────────────────────────────────────────────────────────────────────────

    public ActiveGamePanel(GuiGameEngine engine) {
        this.engine = engine;

        // ── Narrative text area ───────────────────────────────────────────────
        narrativeArea = new JTextArea();
        narrativeArea.setFont(new Font("Courier New", Font.PLAIN, 13));
        narrativeArea.setBackground(NARRATIVE_BG);
        narrativeArea.setForeground(NARRATIVE_FG);
        narrativeArea.setCaretColor(NARRATIVE_FG);
        narrativeArea.setLineWrap(true);
        narrativeArea.setWrapStyleWord(true);   // wrap at word boundaries
        narrativeArea.setEditable(false);       // read-only
        narrativeArea.setBorder(new EmptyBorder(16, 18, 16, 18));

        // Wrap in scroll pane; hide border for a cleaner look
        narrativeScroll = new JScrollPane(narrativeArea);
        narrativeScroll.setBorder(BorderFactory.createEmptyBorder());
        narrativeScroll.getViewport().setBackground(NARRATIVE_BG);
        // Always show vertical scrollbar to avoid layout jumps
        narrativeScroll.setVerticalScrollBarPolicy(
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        // ── Choice button panel ───────────────────────────────────────────────
        // GridLayout(0, 1): 0 means "as many rows as needed", 1 column.
        choicePanel = new JPanel(new GridLayout(0, 1, 0, 4));
        choicePanel.setBackground(BG_COLOR);
        choicePanel.setBorder(new EmptyBorder(10, 12, 12, 12));

        // ── Root panel assembly ───────────────────────────────────────────────
        setBackground(BG_COLOR);
        setLayout(new BorderLayout());
        add(narrativeScroll, BorderLayout.CENTER);
        add(choicePanel,     BorderLayout.SOUTH);
    }

    // ────────────────────────────────────────────────────────────────────────
    // Core public method
    // ────────────────────────────────────────────────────────────────────────

    /**
     * Reads the given scene and refreshes the entire panel to match it.
     *
     * Three cases are handled:
     *  1. Normal StoryScene — show narrative + one button per choice.
     *  2. DeathScene (isGameOver) — show narrative + single "Return to Menu" button.
     *  3. EndingScene (isEnding)  — show narrative + single "Return to Menu" button
     *                               styled differently to signal victory.
     *
     * @param currentScene The scene to render. Must not be null.
     */
    public void updateDisplay(Scene currentScene) {
        // ── 1. Update narrative text ──────────────────────────────────────────
        narrativeArea.setText(currentScene.getNarrative());

        // Scroll back to the top so the player reads from the beginning
        narrativeArea.setCaretPosition(0);

        // ── 2. Rebuild choice buttons ─────────────────────────────────────────
        choicePanel.removeAll(); // clear previous buttons

        if (currentScene.isGameOver()) {
            // Death scene — single prominent "return" button in red tones
            choicePanel.add(buildSpecialButton(
                "[ You Died ]  Return to Main Menu",
                GAMEOVER_BG, GAMEOVER_FG,
                e -> engine.showMenu()
            ));

        } else if (currentScene.isEnding()) {
            // Ending scene — single "return" button in green tones
            choicePanel.add(buildSpecialButton(
                "[ You Survived ]  Return to Main Menu",
                ENDING_BG, ENDING_FG,
                e -> engine.showMenu()
            ));

        } else {
            // Normal scene — one button per available choice
            ArrayList<Choice> choices = currentScene.getChoices();
            for (Choice choice : choices) {
                choicePanel.add(buildChoiceButton(choice));
            }
        }

        // ── 3. Force Swing to re-layout and repaint ───────────────────────────
        choicePanel.revalidate();
        choicePanel.repaint();
    }

    // ────────────────────────────────────────────────────────────────────────
    // Private button factories
    // ────────────────────────────────────────────────────────────────────────

    /**
     * Builds a standard choice button for a regular story scene.
     * Clicking it tells the engine to navigate to the choice's target scene.
     *
     * @param choice The Choice whose label and target this button represents.
     * @return A styled, wired JButton.
     */
    private JButton buildChoiceButton(Choice choice) {
        // Format: "A.  Go straight"
        String label = choice.getLabel() + ".   " + choice.getText();
        JButton btn = new JButton(label);

        btn.setFont(new Font("Courier New", Font.PLAIN, 13));
        btn.setForeground(CHOICE_FG);
        btn.setBackground(CHOICE_BG);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 14, 8, 14));

        // Navigate to the next scene when clicked
        btn.addActionListener(e -> engine.navigateTo(choice.getNextSceneId()));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(CHOICE_HOVER_BG);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(CHOICE_BG);
            }
        });

        return btn;
    }

    /**
     * Builds a full-width "special" button used for game-over and ending states.
     * These are visually distinct from normal choice buttons.
     *
     * @param label    Button text.
     * @param bg       Background colour.
     * @param fg       Foreground (text) colour.
     * @param action   ActionListener to fire on click.
     * @return A styled, wired JButton.
     */
    private JButton buildSpecialButton(
            String label,
            Color bg, Color fg,
            java.awt.event.ActionListener action) {

        JButton btn = new JButton(label);

        btn.setFont(new Font("Courier New", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 14, 10, 14));

        btn.addActionListener(action);

        return btn;
    }
}
