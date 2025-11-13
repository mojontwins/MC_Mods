package org.mojontwins.minecraft.betterbooks;

import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.mod_betterBooks;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiWritableBook extends GuiContainer {
	private int textureId;
	int xSize = 256;
	int ySize = 160+24;
	int currentPage = 0;
	
	private ItemStack theBookStack;
	private TextEditor theEditor;
	private int cursorX;
	private int cursorY;
	private int editPage;
	private EntityPlayer thePlayer;
	
	private GuiTextField textFieldTitle;
	private String theTitle;
	
	public GuiWritableBook(EntityPlayer thePlayer) {
		// ContainerWritableBook has only one slot that's a reference to the player's selected slot
		super(new ContainerWritableBook(thePlayer.inventory, thePlayer.inventory.currentItem));
		
		this.theBookStack = thePlayer.inventory.getCurrentItem();
		this.thePlayer = thePlayer;
		String text;
		
		if(this.theBookStack.hasTagCompound()) {
			NBTTagCompound nbt = this.theBookStack.getTagCompound();
			text = nbt.getString("text");
			this.currentPage = nbt.getInteger("page");
			this.theTitle = nbt.getString("title");
		} else {
			text = "";
			theTitle = "Empty Book";
		}

		this.theEditor = new TextEditor(text);
		this.editPage = 0;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		
		this.theEditor.withFontRenderer(this.fontRenderer);
		
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, y + this.ySize - 20, 200, 20, "Close Book"));
		
		this.textFieldTitle = new GuiTextField(this.fontRenderer, this.width / 2 - 70, y - 24, 170, 20);
		this.textFieldTitle.func_50033_b(false); 	// setFocused
		this.textFieldTitle.setText(theTitle);
		
		try {
			this.textureId = this.mc.renderEngine.allocateAndSetupTexture(ModLoader.loadImage(this.mc.renderEngine, "/org/mojontwins/minecraft/betterbooks/GuiWritableBook.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveAndClose() {
		if(this.theEditor.text.length() > 0) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("text", this.theEditor.text);
			nbt.setInteger("page", this.currentPage);
			this.theTitle = this.textFieldTitle.getText();
			if(!"".equals(this.theTitle)) nbt.setString("title", this.theTitle);
			this.theBookStack.setTagCompound(nbt);
			
			// SMP - We have to send the strings to the server
			
			Packet230ModLoader packet = new Packet230ModLoader();
			packet.packetType = mod_betterBooks.packetType;
			packet.dataString = new String [] {
					this.textFieldTitle.getText(),
					this.theEditor.text
			};
			packet.dataInt = new int [] {
					this.thePlayer.inventory.currentItem,
					this.currentPage
			};
			ModLoaderMp.sendPacket(ModLoaderMp.getModInstance(mod_betterBooks.class), packet);
		}
		this.mc.displayGuiScreen(null);
		this.mc.setIngameFocus();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			this.saveAndClose();
		}
	}
	
	@Override
	protected void mouseClicked (int mouseX, int mouseY, int mouseB) {
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		if(this.currentPage > 0 && 
				this.hoveringPageButton(x, y + 128, mouseX, mouseY) != 0) {
			this.currentPage -= 2;
		}
		
		if(this.currentPage + 1 < this.theEditor.totalPages && 
				this.hoveringPageButton(x + 224, y + 128, mouseX, mouseY) != 0) {
			this.currentPage += 2;
		}
		
		this.editPage = -1;
		
		// Click on left page
		if(this.mouseInPage(x + 16, y + 8, mouseX, mouseY)) {
			this.theEditor.setCursorFromCoords(mouseX - (x + 16), mouseY - (y + 8), this.currentPage);
			this.editPage = 0;
		}
		
		if(this.mouseInPage(x + 128 + 8, y + 8, mouseX, mouseY) && this.theEditor.totalPages > this.currentPage + 1) {
			this.theEditor.setCursorFromCoords(mouseX - (x + 128 + 8), mouseY - (y + 8), this.currentPage + 1);
			this.editPage = 1;
		}
		
		this.textFieldTitle.mouseClicked(mouseX, mouseY, mouseB);
		super.mouseClicked(mouseX, mouseY, mouseB);
	}
	
	@Override
	protected void keyTyped(char c, int scan) {
		//System.out.println ("C " + c + " SCAN " + scan);
		if(this.editPage >= 0) {
			switch(scan) {
			case 1: 
				// ESC
				this.saveAndClose();
				break;
				
			case 14:
				// Backspace
				this.theEditor.backspaceAtCursor();
				break;
				
			case 28:
				// Enter
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					this.theEditor.insertPageBreak();
				} else {
					this.theEditor.insertNewLine();
				}
				break;
				
			case 199:
				// Home
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					this.theEditor.cursorFirstPos();
				} else {
					this.theEditor.cursorHome();
				}
				break;
				
			case 200:
				// Up
				this.theEditor.cursorUp();
				break;
				
			case 201:
				// Page Up
				break;
				
			case 203:
				// Left
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					this.theEditor.cursorPrevWord();
				} else {
					this.theEditor.cursorLeft();
				}
				break;
				
			case 205:
				// Right
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					this.theEditor.cursorNextWord();
				} else {
					this.theEditor.cursorRight();
				}
				break;
				
			case 207:
				// End
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					this.theEditor.cursorLastPos();
				} else {
					this.theEditor.cursorEnd();
				}
				break;
				
			case 208:
				// Down
				this.theEditor.cursorDown();
				break;
				
			case 209:
				// Page down
				break;
				
			case 211:
				// Del
				this.theEditor.deleteAtCursor();
				break;
				
			}
			
			if(c == 22) {
				this.theEditor.addTextAtCursor(this.cleanup(GuiScreen.getClipboardString()));
			}
			
			if(ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0){
				this.theEditor.addCharAtCursor(c);
			}
			
			// Page overflow
			if(this.theEditor.cursorPage != this.currentPage + this.editPage) {
				this.currentPage = this.theEditor.cursorPage & -2;
				this.editPage = this.theEditor.cursorPage & 1;
			}
		}
		
		if(this.textFieldTitle.func_50025_j()) { 			// isFocused
			this.textFieldTitle.func_50037_a(c, scan);		// keyTyped
		}
	}
	
	private String cleanup(String clipboardString) {
		String filtered = "";
		for(char c : clipboardString.toCharArray()) {
			if(ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0) {
				filtered += c;
			}
		}
		
		return filtered;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		this.drawDefaultBackground();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.textureId);
		this.drawTexturedModalRect(x, y, 0, 0, 256, 160);
		
		// Add prev/next page buttons & page numbers
		if(this.currentPage > 0) {
			this.drawTexturedModalRect(x, y + 128, 0 + this.hoveringPageButton(x, y + 128, mouseX, mouseY), 224, 32, 32);
		}
		
		if(this.currentPage + 1 < this.theEditor.totalPages) {
			this.drawTexturedModalRect(x + 224, y + 128, 32 + this.hoveringPageButton(x + 224, y + 128, mouseX, mouseY), 224, 32, 32);
		}
		
		// Render pages
		this.renderPage(this.currentPage, x + 16, y + 8);
		this.renderPage(this.currentPage + 1, x + 128 + 8, y + 8);
		
		// Render page numbers
		this.fontRenderer.drawString("" + this.currentPage, x + (this.currentPage > 0 ? 32 : 16), y + 144, 0xCC4F483D);
		this.fontRenderer.drawString("" + (this.currentPage + 1), x + 256 - (this.currentPage + 1 < this.theEditor.totalPages ? 32 : 16) - this.fontRenderer.getStringWidth("" + (this.currentPage + 1)), y + 144, 0xCC4F483D);		
		
		// Render cursor
		if(this.editPage >= 0 && System.currentTimeMillis() % 1000 < 500) {
			if(this.editPage == 0) {
				this.cursorX = this.theEditor.cursorX + x + 16;
				this.cursorY = this.theEditor.cursorY + y + 8;
				
			} else {
				this.cursorX = this.theEditor.cursorX + x + 128 + 8;
				this.cursorY = this.theEditor.cursorY + y + 8;
				
			}
			
			Gui.drawRect(this.cursorX - 1, this.cursorY - 1, this.cursorX, this.cursorY + 1 + this.fontRenderer.FONT_HEIGHT, 0xFF000000);
		}
		
		// Add title box
		this.fontRenderer.drawString("Title:", this.width / 2 - 100, y - 18, 0xA0A0A0);
		this.textFieldTitle.drawTextBox();
		
		//super.drawScreen(mouseX, mouseY, partialTicks);
		// Usually you'd call super.drawScreen, but we won't, as we don't want the slot to be drawn.
		// That's why we have to render controls ourselves (GuiContainer's would call its super's, which does this)
		
		for (int i = 0; i < this.controlList.size(); ++ i) {
            ((GuiButton)this.controlList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }
	}
	
	private boolean mouseInPage(int x0, int y0, int mouseX, int mouseY) {
		return mouseX >= x0 && mouseX < x0 + this.theEditor.width &&
				mouseY >= y0 && mouseY <= y0 + this.theEditor.linesPerPage * this.theEditor.lineHeight;
	}
	
	private int hoveringPageButton(int x0, int y0, int mouseX, int mouseY) {
		return mouseX >= x0 && mouseX < x0 + 64 && mouseY > y0 && mouseY <= y0 + 64 ? 64 : 0; 
	}
	
	private void renderPage(int page, int x, int y) {
		for(int i = 0; i < this.theEditor.linesPerPage; i ++) {
			String textLine = this.theEditor.getLine(page, i);
			if(textLine != null) {
				this.fontRenderer.drawString(textLine, x, y + this.theEditor.lineHeight * i, 0xCC4F483D);
			}
		}
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {}
}
