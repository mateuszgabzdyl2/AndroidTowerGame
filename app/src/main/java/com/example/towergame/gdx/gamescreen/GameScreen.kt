package com.example.towergame.gdx.gamescreen

import android.content.IntentSender
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.towergame.constants.Constants
import com.example.towergame.gdx.MyGame
import com.example.towergame.viewmodel.VM
import kotlin.math.floor

class GameScreen(
    private val game: MyGame,
    private val onActivityFinish: () -> Unit
) : Screen {
    private val vm: VM = VM()
    private val playerView: PlayerView = PlayerView(vm)
    private val shapeRenderer = ShapeRenderer()
    private val gameCamera = OrthographicCamera()
    private val gameViewport = FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, gameCamera)

    private val staticCamera = OrthographicCamera()
    private val staticViewport = ScreenViewport(staticCamera)
    private val font = BitmapFont()
    private val uiStage = Stage(staticViewport)
    private lateinit var pauseOverlay: PauseOverlay
    private lateinit var gameOverOverlay: GameOverOverlay
    private var isGameOverHandled = false

    // background
    private var background = BackgroundRenderer()
    private val sideWalls = SideWallsRenderer(viewportWidth = staticViewport.worldWidth, viewportHeight = staticViewport.worldHeight)

    private lateinit var animator: PlayerAnimator

    private val leftTex   = Texture("platforms/tileYellow_1.png")
    private val midTex    = Texture("platforms/tileYellow_2.png")
    private val rightTex  = Texture("platforms/tileYellow_3.png")

    override fun show() {
        staticCamera.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0f)
        staticCamera.update()
        gameCamera.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0f)
        gameCamera.update()
        font.data.setScale(2.5f)

        pauseOverlay = PauseOverlay(
            stage = uiStage,
            onResume = {  },
            onRestart = { game.setScreen(GameScreen(game, onActivityFinish)) },
            onMenu = { onActivityFinish() }
        )

        gameOverOverlay = GameOverOverlay(
            stage = uiStage,
            onRestart = { game.setScreen(GameScreen(game, onActivityFinish)) },
            onMenu = { onActivityFinish() }
        )

        val playerAssets = PlayerAssets()
        animator = PlayerAnimator(playerAssets)

        Gdx.input.inputProcessor = uiStage
    }

    override fun render(delta: Float) {
        if(pauseOverlay.isPaused || gameOverOverlay.isVisible) {
            shapeRenderer.projectionMatrix = staticCamera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.color = Color(0f, 0f, 0f, 0.5f)
            shapeRenderer.rect(0f, 0f, staticViewport.worldWidth, staticViewport.worldHeight)
            shapeRenderer.end()
        }

        if(!pauseOverlay.isPaused && !gameOverOverlay.isVisible) {
            vm.update(delta)
            playerView.update()
            gameCamera.position.y = vm.getCameraPosition()
            gameCamera.update()
        }

        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1f)

        // draw scrolling world
        gameViewport.apply()
        game.batch.projectionMatrix = gameCamera.combined
        background.render(game.batch, gameCamera.position.y)

        // draw platforms
        drawPlatforms(game.batch, vm.getPlatformRects())

        // draw players
        animator.update(delta)
        val frame = animator.getFrame(vm.getState())

        val player: Rectangle = vm.getPlayerRects()[0]
        game.batch.begin()
        game.batch.draw(
            frame,
            player.x,
            player.y,
            player.width,
            player.height
        )
        game.batch.end()

        staticViewport.apply()
        game.batch.projectionMatrix = staticCamera.combined
        sideWalls.render(game.batch, gameCamera.position.y)

        // score
        game.batch.projectionMatrix = staticCamera.combined
        game.batch.begin()

        font.color = Color.WHITE
        font.draw(game.batch, "Score: ${vm.getScore()}", 0f, Constants.WORLD_HEIGHT - 20f)

        game.batch.end()

        if(vm.isGameOver() && !isGameOverHandled) {
            gameOverOverlay.show(vm.getScore())
            isGameOverHandled = true
        }

        uiStage.act(delta)
        uiStage.draw()
    }

    fun drawPlatforms(
        batch: SpriteBatch,
        platforms: List<Rectangle>
    ) {
        val tileSize = Constants.PLATFORM_HEIGHT

        batch.begin()

        for(p in platforms) {

            val segments = kotlin.math.round(p.width / tileSize).toInt()
            if(segments < 2) continue

            val xStart = p.x
            val y = p.y

            // left tile
            batch.draw(leftTex, xStart, y, tileSize, tileSize)

            // middle tiles
            for(i in 1 until segments - 1) {
                batch.draw(
                    midTex,
                    xStart + i * tileSize,
                    y,
                    tileSize,
                    tileSize
                )
            }

            // right tile
            batch.draw(
                rightTex,
                xStart + (segments - 1) * tileSize,
                y,
                tileSize,
                tileSize
            )
        }

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        staticViewport.update(width, height, true)

        pauseOverlay.resize()
        gameOverOverlay.resize()
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {
        background.dispose()
        sideWalls.dispose()
        shapeRenderer.dispose()
        font.dispose()

        pauseOverlay.dispose()
        gameOverOverlay.dispose()
        uiStage.dispose()
    }
}