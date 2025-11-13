package org.mojontwins.minecraft.betterbooks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.FontRenderer;

public class TextEditor {
	public String text;
	public int width;
	public int linesPerPage;
	public int lineHeight;
	public int totalPages;
	
	private int cursor;
	private List<String> lines;
	private boolean dirtyLines = true;
	private FontRenderer fontRenderer;
	
	public int cursorPos;
	public int cursorLine;
	public int cursorPage;
	public int cursorX, cursorY;
	
	public TextEditor(String text) {
		this(text, 128 - 16 - 8, 13, 10);
	}
	
	public TextEditor(String text, int width, int linesPerPage, int lineHeight) {
		this.text = text;
		if(text == null) text = "";
		this.width = width;
		this.linesPerPage = linesPerPage;
		this.lineHeight = lineHeight;
	}
	
	public TextEditor withFontRenderer(FontRenderer fontRenderer) {
		this.fontRenderer = fontRenderer;
		return this;
	}
	
	public void clear() {
		this.text = "";
	}
	
	public void addTextAtCursor(String s) {
		if(this.cursor > this.text.length()) this.cursor = this.text.length();
		this.text = this.text.substring(0, this.cursor) + s + this.text.substring(this.cursor);
		this.cursor += s.length(); 
		this.dirtyLines = true;
		this.updateCursorXY();
		
	}
	
	public void addCharAtCursor(char c) {
		if(this.cursor > this.text.length()) this.cursor = this.text.length();
		this.text = this.text.substring(0, this.cursor) + c + this.text.substring(this.cursor);
		this.cursor ++; 
		this.dirtyLines = true;
		this.updateCursorXY();
		
	}
	
	public void backspaceAtCursor() {
		if(this.cursor == 0) return;
		this.text = this.text.substring(0, this.cursor - 1) + this.text.substring(this.cursor);
		this.cursor --;
		this.dirtyLines = true;
		this.updateCursorXY();
	}
	
	public void deleteAtCursor() {
		if(this.cursor == this.text.length()) return;
		this.text = this.text.substring(0, this.cursor) + this.text.substring(this.cursor + 1);
		this.dirtyLines = true;
		this.updateCursorXY();
	}
	
	public void insertNewLine() {
		this.addCharAtCursor('\n'); 
	}
	
	public void insertPageBreak() {
		this.addCharAtCursor('\u000C');
	}
	
	public void cursorLeft() {
		if(this.cursor > 0) {
			this.cursor --;
			this.updateCursorXY();
		}
	}
	
	public void cursorRight() {
		if(this.cursor < this.text.length()) {
			this.cursor ++;
			this.updateCursorXY();
		}
	}
	
	public void cursorUp() {
		this.findCursorInLines();
		//this.moveToLineSameHorzPos(this.cursorLine > 0 ? this.cursorLine - 1 : this.cursorLine);
		// Skip blank lines ( = FF)
		int l = this.cursorLine;
		while(l > 0) {
			l --;
			if (this.lines.get(l) != null && this.lines.get(l).length() > 0) {
				break;
			}
		}
		if (l != this.cursorLine) this.moveToLineSameHorzPos(l);
	}
	
	public void cursorDown() {
		this.findCursorInLines();
		//this.moveToLineSameHorzPos(this.cursorLine < this.lines.size() ? this.cursorLine + 1 : this.lines.size());
		// Skip blank lines ( = FF)
		int l = this.cursorLine;
		while(l < this.lines.size() - 1) {
			l ++;
			if (l == this.lines.size() || (this.lines.get(l) != null && this.lines.get(l).length() > 0)) {
				break;
			}
		}
		if (l != this.cursorLine) this.moveToLineSameHorzPos(l);
	}
	
	public void moveToLineSameHorzPos(int destLine) {
		// Move to destLine at rougly the same horz. position.
		String line = this.cursorLine < this.lines.size() ? this.lines.get(this.cursorLine) : " ";
		
		// Get X in pixels
		int x = this.getStringWidth(this.cursorPos >= line.length() ? line : line.substring (0, this.cursorPos));
		
		// Move to destination line
		this.cursorLine = destLine;
		line = this.cursorLine < this.lines.size() ? this.lines.get(this.cursorLine) : " ";
		
		// Recalculate cursorPos
		int widthInChars = line.length() - 1;
		int width = widthInChars == 0 ? 0 : this.getStringWidth(line.substring(0, widthInChars));
		
		if(x > width) {
			this.cursorPos = widthInChars;
		} else {
			this.cursorPos = this.findOffsetInLineUponCurPos(line, x);
		}
		
		// Recalculate cursor
		this.placeCursorAtLineWithOffset(this.cursorLine, this.cursorPos);
		
		this.updateCursorXY();
	}
		
	public void cursorHome() {
		this.findCursorInLines ();
		this.placeCursorAtLineWithOffset (this.cursorLine, 0);
		this.updateCursorXY();
	}
	
	public void cursorEnd() {
		this.findCursorInLines ();
		this.placeCursorAtLineWithOffset (this.cursorLine, this.lines.get(this.cursorLine).length());
		this.updateCursorXY();
	}
	
	public void cursorPrevWord() {
		while(this.text.charAt(this.cursor - 1) != ' ') {
			this.cursor --;
			if(this.cursor == 0) break;
		}
		this.updateCursorXY();
	}
	
	public void cursorNextWord() {
		while(this.text.charAt(this.cursor) != ' ' && this.cursor < this.text.length()) {
			this.cursor ++;
		}
		if(this.cursor < this.text.length()) this.cursor ++;
		this.updateCursorXY();
	}
	
	public void cursorPageUp() {
		this.findCursorInLines();
		if(this.cursorLine == this.cursorPage * this.linesPerPage) {
			if(this.cursorLine > 0) {
				this.cursorLine -= this.linesPerPage;
			}
		} else {
			this.cursorLine = this.cursorPage * this.linesPerPage;
		}
		this.placeCursorAtLineWithOffset (this.cursorLine, 0);
		this.updateCursorXY();
	}
	
	public void cursorPageDown() {
		this.findCursorInLines();
		this.cursorLine = (this.cursorPage + 1) * this.linesPerPage;
		if(this.cursorLine > this.lines.size()) {
			this.cursorLine = this.lines.size();
		}
		this.placeCursorAtLineWithOffset (this.cursorLine, 0);
		this.updateCursorXY();
	}
	
	public void cursorFirstPos() {
		this.cursor = 0;
		this.findCursorInLines();
		this.updateCursorXY();
	}
	
	public void cursorLastPos() {
		this.cursor = this.text.length();
		this.findCursorInLines();
		this.updateCursorXY();
	}
	
	public void updateCursorXY() {
		// Calculate pixel X, Y in page
		
		// This method updates cursorLine and cursorPos (line and char offset in line)
		findCursorInLines();

		this.cursorPage = this.cursorLine / this.linesPerPage;
		this.cursorY = (this.cursorLine - this.cursorPage * this.linesPerPage) * this.lineHeight;
		this.cursorX = this.cursorLine >= this.lines.size() ? 0 : this.getStringWidth(this.lines.get(this.cursorLine).substring(0, this.cursorPos));
		
	}
	
	public String getLine(int page, int lineno) {
		this.breakLines();
		int absoluteIdx = page * this.linesPerPage + lineno; 
		if(absoluteIdx < this.lines.size()) return this.lines.get(absoluteIdx);
		return null;
	}

	public int getPageFirstLine(int page) {
		return page * linesPerPage;
	}
	
	public int getPageLastLine(int page) {
		return (page + 1) * linesPerPage - 1;
	}
	
	public int getCurrentLineNumber() {
		this.findCursorInLines();
		
		return this.cursorLine;
	}
	
	public void setCursorFromCoords(int x, int y, int page) {
		// User has clicked on a page in the book. x, y are the offsets from the top-right corner.
		
		// Calculate which absolute line based on Y and which page we are in
		this.cursorLine = page * linesPerPage + y / lineHeight;
		if(this.cursorLine >= this.lines.size()) this.cursorLine = this.lines.size() - 1;
		
		String line = this.lines.get(this.cursorLine);
		
		// Find which string index in the current line contains the pixel at x
		int offs = this.cursorLine >= this.lines.size() ? 0 : this.findOffsetInLineUponCurPos(line, x);
		
		// Now calculate the absolute cursor.
		this.placeCursorAtLineWithOffset(this.cursorLine, offs);
		
		// Pixel coordinates of cursor
		this.cursorY = (this.cursorLine - page * linesPerPage) * this.lineHeight;
		this.cursorX = this.cursorLine >= this.lines.size() ? 0 : this.getStringWidth(offs >= line.length() ? line : line.substring(0, offs));
		
	}

	private void breakLines() {
		// Breaks lines with wordwrap.
		
		// Stored lines have the ending character that broke the string,
		// whatever it is, space or \n. Too long words won't have any.
		
		if(this.dirtyLines) {
			this.lines = new ArrayList<String> ();
			
			// I have to do a manual scan 
			String curWord = "";
			String curLine = "";
			String treatedText = this.text + " ";
			char spacer = ' ';
			for(int i = 0; i < treatedText.length(); i ++) {
				char c = treatedText.charAt(i);
				
				if(c == ' ' || c == '\n' || c == '\u000C') {
					spacer = c == '\u000C' ? '\u2937' : ' ';
					
					if(curWord.length() == 0) {
						if(this.getStringWidth(curLine + " ") < this.width) {
							curLine = curLine + spacer;
						} else {
							this.lines.add(curLine + spacer);
							curLine = "";
						}
					} else {
						if(this.getStringWidth(curLine + curWord) < this.width) {
							curLine = curLine + curWord + spacer;
							curWord = "";
						} else {
							this.lines.add(curLine);
							if(this.getStringWidth(curWord) > this.width) {
								curLine = "";
								int j = 0; while(j < curWord.length()) {
									if(this.getStringWidth(curLine + curWord.charAt(j)) > this.width) {
										this.lines.add(curLine);
										curLine = "";
										curWord = curWord.substring(j);
										j = 0;
									} else {
										curLine = curLine + curWord.charAt(j);
										j ++;
									}
								}
							}
							curLine = curWord + spacer;
							curWord = "";
						}
					}					
					
					if (c == '\n' || c == '\u000C') {
						this.lines.add(curLine);
						curLine = "";
					} 
					
					if (c == '\u000C') {
						// Insert blank lines to the end of the page.
						int nextPageFirstLine = this.linesPerPage * (1 + (this.lines.size() - 1) / this.linesPerPage);
						while(this.lines.size() < nextPageFirstLine) {
							this.lines.add("");
						}
					}
				} else {
					curWord = curWord + c;
				}
			}
			if(curLine.length() > 0) this.lines.add(curLine); 
			
			this.totalPages = this.lines.size() / this.linesPerPage + 1;
			this.dirtyLines = false;
		}
	}
	
	public int getStringWidth(String s) {
		return this.fontRenderer.getStringWidth(s);
	}
	
	public void findCursorInLines() {
		// Finds in which line is the cursor.
		// This method needs this.lines to be UPDATED
		this.breakLines();
		
		int charCtr = 0;
		
		this.cursorLine = 0;
		for(String curLine : this.lines) {
			this.cursorPos = 0;
			for(char c : curLine.toCharArray()) {
				if(this.cursor == charCtr) {
					return;
				}
				this.cursorPos ++;
				charCtr ++;
			}
			this.cursorLine ++;
		}
	}
	
	// offs is in pixels. Find which string index contains that position
	public int findOffsetInLineUponCurPos(String line, int offs) {
		int l = line.length();
		for(int i = 0; i < l ; i ++) {
			if(this.getStringWidth(line.substring(0, i)) >= offs) {
				return i;
			} 
		}
		
		return l;
	}
	
	// Go to lineNo, character #offs
	private void placeCursorAtLineWithOffset(int lineNo, int offs) {
		this.cursor = offs;
		for(int i = 0; i < lineNo; i ++) {
			this.cursor += this.lines.get(i).length();
		}
	}

}
