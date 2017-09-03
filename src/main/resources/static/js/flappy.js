$(document).ready(function () {
        var game = new Phaser.Game(720, 1280, Phaser.AUTO, 'game_div'); //实例化一个Phaser的游戏实例

        var game_states = {};

        game_states.boot = function () {
            //boot场景，用来做一些游戏启动前的准备
            this.preload = function () {
                game.load.image('loading', '/assets/flappy/preloader.gif');
            };
            this.create = function () {
                game.state.start("preload");
            }
        };

        game_states.preload = function () {
            //preload场景，用来显示资源加载进度
            this.preload = function () {
                var preloadSprite = game.add.sprite(140, game.height / 2, 'loading');
                preloadSprite.scale.setTo(2);
                game.load.setPreloadSprite(preloadSprite);

                //资源
                game.load.image('background', '/assets/flappy/bg.png'); //游戏背景图
                game.load.image('ground', '/assets/flappy/ground.png'); //地面
                game.load.image('get_ready', '/assets/flappy/get-ready.png'); //get ready图片
                game.load.image('play_tip', '/assets/flappy/instructions.png'); //玩法提示图片
                game.load.spritesheet('bird', '/assets/flappy/bird.png', 86, 60, 3); //鸟
                game.load.image('btn', '/assets/flappy/start-button.png');  //按钮
                game.load.spritesheet('pipe', '/assets/flappy/holdback.png', 148, 820, 2); //管道
                game.load.bitmapFont('font', '/assets/flappy/flappyfont/flappyfont.png', '/assets/flappy/flappyfont/flappyfont.fnt');//显示分数的字体
                game.load.audio('fly_sound', '/assets/flappy/flap.wav');//飞翔的音效
                game.load.audio('score_sound', '/assets/flappy/score.wav');//得分的音效
                game.load.audio('hit_pipe_sound', '/assets/flappy/pipe-hit.wav'); //撞击管道的音效
                game.load.audio('hit_ground_sound', '/assets/flappy/ouch.wav'); //撞击地面的音效
                game.load.image('game_over', '/assets/flappy/gameover.png'); //gameover图片
                game.load.image('score_board', '/assets/flappy/scoreboard.png'); //得分板
            };

            this.create = function () {
                game.state.start('play');
                game.scale.scaleMode = Phaser.ScaleManager.USER_SCALE;
                game.scale.setUserScale(0.5);
            };
        };

        game_states.play = function () {
            this.create = function () {
                //debug
                game.time.advancedTiming = true;
                // 游戏名
                var name = game.add.text(20, 20, "Flappy Bird", {font: "30px Arial", fill: "#ffffff"});
                // 背景
                game.add.tileSprite(0, 0, 720, 1280, 'background');
                // 地面
                this.ground = game.add.tileSprite(0, game.height - 281, 840, 281, 'ground');
                this.ground.autoScroll(-350, 0);// 地面滚动
                game.physics.enable(this.ground);
                this.ground.body.immovable = true;
                // title
                this.title = game.add.group();
                var getReady = this.title.create(0, 0, "get_ready");
                var playTip = this.title.create(86, 200, "play_tip").anchor.setTo(0.5);
                this.title.x = 130;
                this.title.y = 350;
                this.title.scale.setTo(2.5);
                // 计分板
                this.scoreTxt = game.add.bitmapText(game.world.centerX - 20, 200, 'font', "0", 72);
                // 创建小鸟
                this.bird = game.add.sprite(190, 650, 'bird'); //创建bird
                this.bird.animations.add('fly'); //给鸟添加动画
                this.bird.animations.play('fly', 12, true); //播放动画
                this.bird.anchor.setTo(0.5, 0.5); //设置中心点
                game.physics.enable(this.bird); //开启鸟的物理系统
                // bird.body.gravity.y = 1000;
                // 缓动
                // to(properties, duration, ease, autoStart, delay, repeat, yoyo)
                this.birdTween = game.add.tween(this.bird).to({y: 600}, 500, null, true, 0, Number.MAX_VALUE, true);

                // 音效
                this.soundFly = game.add.sound('fly_sound');
                this.soundScore = game.add.sound('score_sound');
                this.soundHitPipe = game.add.sound('hit_pipe_sound');
                this.soundHitGround = game.add.sound('hit_ground_sound');

                // 管道组
                this.pipeGroup = game.add.group();
                this.pipeGroup.enableBody = true;

                // 调整显示顺序
                game.world.bringToTop(name);
                game.world.bringToTop(this.ground);
                game.world.bringToTop(this.bird);
                game.world.bringToTop(this.scoreTxt);

                //
                this.hasStarted = false;
                this.gameIsOver = false;
                this.hasHitGround = false;
                this.score = -1;
                //点击开始游戏
                game.input.onDown.addOnce(this.startGame, this); //点击屏幕后正式开始游戏
            };

            this.update = function () {
                console.log("update");
                if (!this.hasStarted) {
                    return;
                }
                //检测碰撞
                game.physics.arcade.collide(this.bird, this.ground, this.hitGround, null, this); //检测与地面的碰撞
                game.physics.arcade.overlap(this.bird, this.pipeGroup, this.hitPipe, null, this); //检测与管道的碰撞
                // this.pipeGroup.forEachExists(this.checkScore, this); //分数检测和更新

                if (this.bird.body.y < 0) {
                    this.bird.body.y = 0;
                }
            };

            this.startGame = function () {
                this.hasStarted = true;
                this.title.destroy();

                game.tweens.remove(this.birdTween);
                this.bird.body.gravity.y = 2000;
                game.input.onDown.add(this.fly, this);

                game.time.events.loop(1500, this.generatePipes, this); //利用时钟事件来循环产生管道
                game.time.events.loop(1500, this.countScore, this); //利用时钟事件来循环产生管道
                game.time.events.start();
                this.fly();// 先飞一下
            };

            this.stopGame = function () {
                this.ground.stopScroll();
                this.pipeGroup.forEachExists(function (pipe) {
                    pipe.body.velocity.x = 0;
                }, this);
                this.bird.animations.stop('fly', 0);
                game.tweens.remove(this.upTween);
                game.tweens.remove(this.tweenDown);
                game.add.tween(this.bird).to({angle: 80}, 300, null, true, 0, 0, false);
                game.input.onDown.remove(this.fly, this);
                game.time.events.stop(true);
            };

            this.fly = function () {
                game.tweens.remove(this.upTween);
                game.tweens.remove(this.tweenDown);
                this.bird.body.velocity.y = -600;
                this.upTween = game.add.tween(this.bird).to({angle: -30}, 50, null, true, 0, 0, false);
                this.tweenDown = game.add.tween(this.bird).to({angle: 80}, 600, null, false, 350, 0, false);
                this.upTween.chain(this.tweenDown);

                this.soundFly.play();
            };

            this.hitGround = function () {
                if (this.hasHitGround) {
                    return;
                } //已经撞击过地面
                this.hasHitGround = true;
                this.soundHitGround.play();
                this.gameOver(true);
            };

            this.hitPipe = function () {
                if (this.gameIsOver) {
                    return;
                }
                this.soundHitPipe.play();
                this.gameOver();
            };

            this.gameOver = function (show_text) {
                this.gameIsOver = true;
                this.stopGame();
                if (show_text) {
                    this.showGameOverText();
                }
            };

            this.showGameOverText = function () {
                this.scoreTxt.destroy();
                game.bestScore = game.bestScore || 0;
                if (this.score > game.bestScore) {
                    game.bestScore = this.score;
                }
                this.gameOverGroup = game.add.group(); //添加一个组
                var gameOverText = this.gameOverGroup.create(140, 0, 'game_over'); //game over 文字图片
                var scoreboard = this.gameOverGroup.create(140, 70, 'score_board'); //分数板
                var currentScoreText = game.add.bitmapText(140 + 60, 105, 'font', this.score + '', 24, this.gameOverGroup); //当前分数
                var bestScoreText = game.add.bitmapText(140 + 60, 153, 'font', game.bestScore + '', 24, this.gameOverGroup); //最好分数
                var replayBtn = game.add.button(140, 210, 'btn', function () {//重玩按钮
                    game.state.start('play');
                }, this, null, null, null, null, this.gameOverGroup);
                gameOverText.anchor.setTo(0.5, 0);
                scoreboard.anchor.setTo(0.5, 0);
                replayBtn.anchor.setTo(0.5, 0);
                this.gameOverGroup.y = 120;
                this.gameOverGroup.scale.setTo(2.5);
            }

            this.countScore = function () {
                ++this.score;
                if (this.score > 0) {
                    this.soundScore.play(); //得分的音效
                    this.scoreTxt.text = this.score; //更新分数的显示
                }
            }

            this.checkScore = function (pipe) {//负责分数的检测和更新,pipe表示待检测的管道
                //pipe.hasScored 属性用来标识该管道是否已经得过分
                //pipe.y<0是指一组管道中的上面那个管道，一组管道中我们只需要检测一个就行了
                //当管道的x坐标 加上管道的宽度小于鸟的x坐标的时候，就表示已经飞过了管道，可以得分了
                if (!pipe.hasScored && pipe.y <= 0 && pipe.x <= this.bird.x - 43 - 74) {
                    pipe.hasScored = true; //标识为已经得过分
                    this.scoreTxt.text = ++this.score; //更新分数的显示
                    this.soundScore.play(); //得分的音效
                    return true;
                }
                return false;
            }

            this.generatePipes = function () {
                var gap = 200; //上下管道之间的间隙宽度
                var posMin = 100;
                var posMax = 750;
                var position = posMin + Math.floor((posMax - posMin) * Math.random());//计算出一个上下管道之间的间隙的随机位置
                var topPipeY = position - 820; //上方管道的位置
                var bottomPipeY = position + gap; //下方管道的位置

                //如果有出了边界的管道，则重置他们，不再制造新的管道了,达到循环利用的目的
                if (this.resetPipe(topPipeY, bottomPipeY)) {
                    return;
                }

                var topPipe = game.add.sprite(game.width, topPipeY, 'pipe', 1, this.pipeGroup); //上方的管道
                var bottomPipe = game.add.sprite(game.width, bottomPipeY, 'pipe', 0, this.pipeGroup); //下方的管道
                bottomPipe.sendToBack();
                this.pipeGroup.setAll('checkWorldBounds', true); //边界检测
                this.pipeGroup.setAll('outOfBoundsKill', true); //出边界后自动kill
                this.pipeGroup.setAll('body.velocity.x', -350); //设置管道运动的速度
            };

            this.resetPipe = function (topPipeY, bottomPipeY) {//重置出了边界的管道，做到回收利用
                var i = 0;
                this.pipeGroup.forEachDead(function (pipe) { //对组调用forEachDead方法来获取那些已经出了边界，也就是“死亡”了的对象
                    if (pipe.y <= 0) { //是上方的管道
                        pipe.reset(game.width, topPipeY); //重置到初始位置
                        pipe.hasScored = false; //重置为未得分
                    } else {//是下方的管道
                        pipe.reset(game.width, bottomPipeY); //重置到初始位置
                    }
                    pipe.body.velocity.x = -350; //设置管道速度
                    i++;
                }, this);
                return i == 2; //如果 i==2 代表有一组管道已经出了边界，可以回收这组管道了
            }

            this.render = function () {
                // 显示精灵体
                game.debug.text("FPS: " + game.time.fps, 100, 100);
            }
        };

        game.state.add('boot', game_states.boot);
        game.state.add('preload', game_states.preload);
        game.state.add('play', game_states.play);
        // 开始游戏
        game.state.start('boot');
    }
);
