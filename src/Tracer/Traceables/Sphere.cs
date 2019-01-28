using System;
using System.Drawing;
using System.Linq;
using System.Numerics;
using static System.Math;

namespace Tracer.Traceables
{
    public class Sphere : Traceable
    {
        private Vector3 _center;
        private float _radius;

        //public Sphere() { }
        public Sphere(Vector3 center, float radius)
        {
            _center = center;
            _radius = radius;
        }

        public override bool Hit(Ray ray, float tmin, float tmax, out HitRecord hit)
        {
            var oc = ray.Origin - _center;
            var a = Vector3.Dot(ray.Direction, ray.Direction);
            var b = Vector3.Dot(oc, ray.Direction);
            var c = Vector3.Dot(oc, oc) - _radius * _radius;
            var discriminant = b * b - a * c;

            if (discriminant > 0)
            {
                // Would be nice to use MathF here, but its not available yet in netstandard
                var temp = (float)Sqrt(b * b - a * c);
                var t = (-b - temp) / a;

                if (t < tmax && t > tmin)
                {
                    hit = new HitRecord
                    {
                        T = t,
                        Position = ray.PointAt(t),
                        Normal = (ray.PointAt(t) - _center) / _radius,                        
                    };
                    return true;
                }

                t = (-b + temp) / a;

                if (t < tmax && t > tmin)
                {
                    hit = new HitRecord
                    {
                        T = t,
                        Position = ray.PointAt(t),
                        Normal = (ray.PointAt(t) - _center) / _radius,                        
                    };
                    return true;
                }
            }
            hit = new HitRecord();
            return false;
        }
    }
}