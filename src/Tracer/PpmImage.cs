using System;
using System.Drawing;
using System.IO;
using System.Numerics;
using System.Threading.Tasks;

namespace Tracer
{
    public class PpmImage
    {
        private int _width;
        private int _height;
        private Vector3[,] _raster;

        public PpmImage()
            : this(500, 500)
        { }

        public PpmImage(int width, int height)
            : this(width, height, Color.Black)
        { }

        public PpmImage(int width, int height, Color background)
        {
            _width = width;
            _height = height;
            _raster = new Vector3[width,height];

            var bg = background.ToVector();

            for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
            {
                _raster[x,y] = bg;
            }
        }

        public int Width => _width;
        public int Height => _height;

        public bool Set(int x, int y, Color color)
            => Set(x, y, color.ToVector());

        public bool Set(int x, int y, Vector3 color)
        {
            if (0 > x || x > _width) return false;
            if (0 > y || y > _height) return false;

            _raster[x, y] = color;
            return true;
        }

        public void GammaCorrect(float gamma = 2.0f)
        {
            Vector3 temp;
            var power = 1.0f / gamma;

            for (int x = 0; x < _width; x++)
            for (int y = 0; y < _height; y++)
            {
                temp = _raster[x,y];
                _raster[x,y] = new Vector3(
                    (float)Math.Pow(temp.X, power),
                    (float)Math.Pow(temp.Y, power),
                    (float)Math.Pow(temp.Z, power)
                );
            }
        }

        public async Task Save(Stream outStream)
        {
            using (var writer = new StreamWriter(outStream))
            {
                await writer.WriteLineAsync("P3");
                await writer.WriteLineAsync($"{_width} {_height}");
                await writer.WriteLineAsync("255");

                Color color;

                for (int x = 0; x < _width; x++)
                for (int y = 0; y < _height; y++)
                {
                    color = _raster[x,y].ToColor();
                    await writer.WriteLineAsync($"{color.R} {color.G} {color.B}");
                }
            }
        }
    }
}