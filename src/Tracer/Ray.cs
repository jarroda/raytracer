using System.Numerics;

namespace Tracer
{    
    public class Ray
    {
        private Vector3 _origin = new Vector3(0, 0, 0);
        private Vector3 _direction = new Vector3(0, 0, 1);

        public Ray(Vector3 point1, Vector3 point2)
        {
            SetPoints(point1, point2);
        }

        public void SetPoints(Vector3 point1, Vector3 point2)
        {
            _origin = point1;
            _direction = Vector3.Subtract(point2, point1);
        }

        public void SetPointDirection(Vector3 point, Vector3 direction)
        {
            _origin = point;
            _direction = direction;
        }

        public Vector3 PointAt(float t)
        {
            return Vector3.Add(_origin, Vector3.Multiply(_direction, t));
        }

        public Vector3 Origin { get => _origin; }

        public Vector3 Direction { get => _direction; }
    }
}