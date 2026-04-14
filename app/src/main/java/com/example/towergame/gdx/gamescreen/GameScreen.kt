package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.example.towergame.constants.Constants
import com.example.towergame.gdx.MyGame
import com.example.towergame.viewmodel.VM

class GameScreen(
    private val game: MyGame,
    private val onGameOver: () -> Unit
) : Screen {
    private val vm: VM = VM()
    private val playerView: PlayerView = PlayerView(vm)
    private val shapeRenderer = ShapeRenderer()
    private val gameCamera = OrthographicCamera()
    private val gameViewport = FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, gameCamera)

    private val staticCamera = OrthographicCamera()
    private val staticViewport = FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, staticCamera)
    private var started = false
    private val font = BitmapFont()


    override fun show() {
        staticCamera.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0f)
        staticCamera.update()
        gameCamera.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0f)
        gameCamera.update()
        font.data.setScale(2.5f)
    }

    override fun render(delta: Float) {
        if(!started) {
            if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                started = true
            }
        }

        if(started) {
            vm.update(delta)
            playerView.update()
            gameCamera.position.y = vm.getCameraPosition()
            gameCamera.update()
        }

        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1f)

        // draw scrolling world
        gameViewport.apply()
        shapeRenderer.projectionMatrix = gameCamera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        // draw platforms
        shapeRenderer.color = Color.LIGHT_GRAY
        for(plat in vm.getPlatformRects()) {
            shapeRenderer.rect(plat.x, plat.y, plat.width, plat.height)
        }

        // draw players
        shapeRenderer.color = Color.ORANGE
        for(player in vm.getPlayerRects()) {
            shapeRenderer.rect(player.x, player.y, player.width, player.height)
        }

        shapeRenderer.end()

        staticViewport.apply()
        shapeRenderer.projectionMatrix = staticCamera.combined

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        shapeRenderer.color = Color.DARK_GRAY
        shapeRenderer.rect(0f, 0f, Constants.WALL_WIDTH, Constants.WORLD_HEIGHT)
        shapeRenderer.rect(Constants.WORLD_WIDTH - Constants.WALL_WIDTH, 0f, Constants.WALL_WIDTH, Constants.WORLD_HEIGHT)

        shapeRenderer.end()

        if(vm.isGameOver()) {
            onGameOver()
        }

        // score
        game.batch.projectionMatrix = staticCamera.combined

        game.batch.begin()
        font.color = Color.WHITE
        font.draw(game.batch, "Score: ${vm.getScore()}", 0f, Constants.WORLD_HEIGHT - 20f)
        game.batch.end()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        staticViewport.update(width, height, true)
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {
        shapeRenderer.dispose()
        game.batch.dispose()
        font.dispose()
    }
}