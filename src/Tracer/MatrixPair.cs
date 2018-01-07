using System.Numerics;

namespace Tracer
{    
    public struct MatrixPair
    {
        private Matrix4x4 _origin;
        private Matrix4x4 _inverse;

        public void Translate(float x, float y, float z)
        {
            var m = Matrix4x4.CreateTranslation(x, y, z);
            
            _origin = _origin * m;
            _inverse = _inverse * m;
        }

        public void Scale(float sx, float sy, float sz)
        {
            var m = Matrix4x4.CreateScale(sx, sy, sz);

            _origin = _origin * m;
            _inverse = _inverse * m;
        }

        public Matrix4x4 Origin => _origin;
        public Matrix4x4 Inverse => _inverse;
    }
}